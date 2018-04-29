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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsu.netcracker.parking.frontend.json.EvacServiceJsonConverter;
import ru.vsu.netcracker.parking.frontend.json.JsonConverter;
import ru.vsu.netcracker.parking.frontend.objects.*;
import ru.vsu.netcracker.parking.frontend.security.CustomSHA256PasswordEncoder;
import ru.vsu.netcracker.parking.frontend.utils.CustomRestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class ObjService {

    private RestTemplate parkingBackendRestTemplate;
    private CustomRestTemplate evacServiceRestTemplate;
    private Attributes attributes;
    private JsonConverter jsonConverter;
    private EvacServiceJsonConverter evacServiceJsonConverter;
    private CustomSHA256PasswordEncoder passwordEncoder;

    @Autowired
    public ObjService(RestTemplate parkingBackendRestTemplate, CustomRestTemplate evacServiceRestTemplate, Attributes attributes, JsonConverter jsonConverter, EvacServiceJsonConverter evacServiceJsonConverter, CustomSHA256PasswordEncoder passwordEncoder) {
        this.parkingBackendRestTemplate = parkingBackendRestTemplate;
        this.evacServiceRestTemplate = evacServiceRestTemplate;
        this.attributes = attributes;
        this.jsonConverter = jsonConverter;
        this.evacServiceJsonConverter = evacServiceJsonConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        initAttributes();
        initParkingsCache();
    }

    public void initAttributes() {
        Map<Long, String> attrs = parkingBackendRestTemplate.getForObject("restapi/attributes", Map.class);
        attributes.setAll(attrs);
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
    public Obj get(long id) {
        JsonNode jsonNode = parkingBackendRestTemplate.getForObject("restapi/" + id, JsonNode.class);
        Obj obj = jsonConverter.jsonToObject(jsonNode);
        return obj;
    }

    public Map<Long, Obj> getAll() {
        return Hazelcast.getHazelcastInstanceByName("hazelcast-instance").getMap("objects-cache");
    }


    @CachePut(value = "objects-cache", key = "#obj.id", unless = "#result.typeId != 3")
    public Obj save(Obj obj) {
        JsonNode jsonNode = jsonConverter.objToJson(obj);
        parkingBackendRestTemplate.put("restapi/" + obj.getId(), obj, JsonNode.class);
        return obj;
    }

    @CacheEvict(value = "objects-cache", key = "#id")
    public void delete(long id) {
        parkingBackendRestTemplate.delete("restapi/" + id);
    }

    public Obj getObjByUsername(String username){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>("\"" + username + "\"", headers);
        ResponseEntity<JsonNode> response = parkingBackendRestTemplate.exchange("restapi/get-user", HttpMethod.POST, entity, JsonNode.class);
        JsonNode jsonNode = response.getBody();
        Obj obj = jsonConverter.jsonToObject(jsonNode);
        return obj;
    }

    private final long EMAIL_ATTRIBUTE_ID = 202L;
    private final long PASSWORD_ATTRIBUTE_ID = 203L;

    public void registerNewUser(Obj obj) {
        String rawPassword = obj.getValues().get(PASSWORD_ATTRIBUTE_ID);
        String encodedPassword = passwordEncoder.encode(rawPassword, obj.getValues().get(EMAIL_ATTRIBUTE_ID).getBytes());
        obj.getValues().put(PASSWORD_ATTRIBUTE_ID, encodedPassword);
        this.save(obj);
    }

    // ToDo IMPLEMENT EVAC SERVICE INTEGRATION BELOW

    public JsonNode sendEvacRequest(long parkingId) {
        Obj parking = get(parkingId);
        JsonNode jsonNode = evacServiceJsonConverter.createJsonRequest(parking);
        return jsonNode;
        //new RestTemplate().postForObject("http://localhost:8081/evac/display-request", jsonNode, JsonNode.class);
        //JsonNode jsonResponse = evacServiceRestTemplate.postForObject("/orders", jsonNode, JsonNode.class);
        //long id = evacServiceJsonConverter.parseJsonResponse(jsonResponse);
        //long id = jsonConverter.extractEvacOrderIdFromJson
        // obj. set evac_order_id
        //update database and cache
    }

    public void updateEvacOrderStatus(Obj obj) {
        //JsonNode jsonNode = jsonConverter.objToJson(obj);
        //evacServiceRestTemplate.postForObject("", jsonNode, JsonNode.class);
    }


}
