package ru.vsu.netcracker.parking.frontend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import ru.vsu.netcracker.parking.frontend.exceptions.ResourceNotFoundException;
import ru.vsu.netcracker.parking.frontend.exceptions.UserAlreadyExistsException;
import ru.vsu.netcracker.parking.frontend.json.JsonConverter;
import ru.vsu.netcracker.parking.frontend.objects.*;
import ru.vsu.netcracker.parking.frontend.security.CustomSHA256PasswordEncoder;
import ru.vsu.netcracker.parking.frontend.utils.CustomRestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class ObjService {

    private RestTemplate parkingBackendRestTemplate;
    private CustomRestTemplate evacServiceRestTemplate;
    private Attributes attributes;
    private JsonConverter jsonConverter;
    private CustomSHA256PasswordEncoder passwordEncoder;

    @Autowired
    public ObjService(RestTemplate parkingBackendRestTemplate, CustomRestTemplate evacServiceRestTemplate, Attributes attributes, JsonConverter jsonConverter, CustomSHA256PasswordEncoder passwordEncoder) {
        this.parkingBackendRestTemplate = parkingBackendRestTemplate;
        this.evacServiceRestTemplate = evacServiceRestTemplate;
        this.attributes = attributes;
        this.jsonConverter = jsonConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        initAttributes();
        initParkingsCache();
    }

    public void initAttributes() {
        Attributes attrs = parkingBackendRestTemplate.getForObject("restapi/attributes", Attributes.class);
        this.attributes.setAll(attrs.getAll());
    }

    public void initParkingsCache() {
        ArrayNode arrayNode = parkingBackendRestTemplate.getForObject("restapi/parkings", ArrayNode.class);
        IMap map = Hazelcast.getHazelcastInstanceByName("hazelcast-instance").getMap("objects-cache");
        for (JsonNode jsonNode : arrayNode) {
            Obj obj = jsonConverter.jsonToObject(jsonNode);
            map.put(obj.getId(), obj);
        }
    }

    @Cacheable(value = "objects-cache", key = "#id", unless = "#result.typeId != 3")
    public Obj get(long id) throws ResourceNotFoundException {
        JsonNode jsonNode = parkingBackendRestTemplate.getForObject("restapi/objects/" + id, JsonNode.class);
        Obj obj = jsonConverter.jsonToObject(jsonNode);
        return obj;
    }

    public Map<Long, Obj> getAll() {
        return Hazelcast.getHazelcastInstanceByName("hazelcast-instance").getMap("objects-cache");
    }

    @CachePut(value = "objects-cache", key = "#result.id", unless = "#result.typeId != 3")
    public Obj save(Obj obj) throws UserAlreadyExistsException {
        JsonNode jsonNode = jsonConverter.objToJson(obj);
        if (obj.getId() == 0) {
            JsonNode jsonResponse = parkingBackendRestTemplate.postForObject("restapi/objects", jsonNode, JsonNode.class);
            obj = jsonConverter.jsonToObject(jsonResponse);
        } else {
            parkingBackendRestTemplate.put("restapi/objects/" + obj.getId(), jsonNode, JsonNode.class);
        }
        if (obj.getTypeId() == 3L) {
            cachePut(obj);
        }
        return obj;
    }

    private void cachePut(Obj obj) {
        Map<Long, Obj> cache = getAll();
        cache.put(obj.getId(), obj);
    }

    @CacheEvict(value = "objects-cache", key = "#id")
    public void delete(long id) {
        parkingBackendRestTemplate.delete("restapi/objects" + id);
    }

    public Obj getObjByUsername(String username) throws IllegalArgumentException, ResourceNotFoundException {
        ResponseEntity<JsonNode> response;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>("\"" + username + "\"", headers);
        response = parkingBackendRestTemplate.exchange("restapi/users/get-user", HttpMethod.POST, entity, JsonNode.class);
        JsonNode jsonNode = response.getBody();
        Obj obj = jsonConverter.jsonToObject(jsonNode);
        return obj;
    }

    private final long OWNER_ID_ATTRIBUTE_ID = 300L;

    public Map<Long, Obj> getAllParkingsOwnedByUser(long userId) {
        Map<Long, Obj> parkings = getAll();
        Map<Long, Obj> ownedParkings = new HashMap<>();

        parkings.forEach((k, v) -> {
            if (v.getReferences().get(OWNER_ID_ATTRIBUTE_ID) == userId) {
                ownedParkings.put(k, v);
            }
        });
        return ownedParkings;
    }

    private final long EMAIL_ATTRIBUTE_ID = 202L;
    private final long PASSWORD_ATTRIBUTE_ID = 203L;
    private final long ROLE_ATTRIBUTE_ID = 200L;
    private final long ROLE_USER_ID = 3L;
    private final long USER_OBJECT_TYPE_ID = 2L;

    public void registerNewUser(Obj obj) throws UserAlreadyExistsException {
        obj.getReferences().put(ROLE_ATTRIBUTE_ID, ROLE_USER_ID);
        String rawPassword = obj.getValues().get(PASSWORD_ATTRIBUTE_ID);
        String encodedPassword = passwordEncoder.encode(rawPassword, obj.getValues().get(EMAIL_ATTRIBUTE_ID).getBytes());
        obj.getValues().put(PASSWORD_ATTRIBUTE_ID, encodedPassword);
        obj.setTypeId(USER_OBJECT_TYPE_ID);
        this.save(obj);
    }

    private final long FREE_SPOTS_COUNT_ID = 307L;
    private final long STATUS_ID = 308L;

    public void takeParking(Obj parking) throws IllegalArgumentException {
        long freeSpotsCount = Long.valueOf(parking.getValues().get(FREE_SPOTS_COUNT_ID));
        if (freeSpotsCount > 0)
            parking.getValues().put(FREE_SPOTS_COUNT_ID, String.valueOf(--freeSpotsCount));
        if (freeSpotsCount == 0)
            parking.getListValues().put(STATUS_ID, "Occupied");
        save(parking);
    }

    /*evacuation service*/

    private final long EVAC_ORDER_ID_ATTR_ID = 330L;
    private final long EVAC_ORDER_STATUS_ATTR_ID = 331L;
    public static final long EVAC_TO_PARKING_ID = 12L;

    public Obj evacParking(long parkingId) {
        JsonNode response = parkingBackendRestTemplate.getForObject("restapi/parkings/" + parkingId + "/evac", JsonNode.class);
        Obj obj = jsonConverter.jsonToObject(response);
        cachePut(obj);
        // checks if evacuation order status updated    (every 15 secs)
        new Thread(() -> {
            while (true) {
                JsonNode jsonNode = parkingBackendRestTemplate.getForObject("restapi/objects/" + obj.getId(), JsonNode.class);
                Obj obj2 = jsonConverter.jsonToObject(jsonNode);
                if (obj2.getValues().get(EVAC_ORDER_STATUS_ATTR_ID).equals("In progress")) {
                    cachePut(obj2);
                } else if (obj2.getValues().get(EVAC_ORDER_STATUS_ATTR_ID).equals("Completed")) {
                    cachePut(obj2);
                    // это, обновляем инфу о парковке, куда мы эвакуируем
                    JsonNode jsonNode2 = parkingBackendRestTemplate.getForObject("restapi/objects/" + EVAC_TO_PARKING_ID, JsonNode.class);
                    Obj evacToParking = jsonConverter.jsonToObject(jsonNode2);
                    cachePut(evacToParking);
                    break;
                }
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return obj;
    }
}
