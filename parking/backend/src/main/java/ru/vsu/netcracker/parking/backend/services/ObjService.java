package ru.vsu.netcracker.parking.backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.netcracker.parking.backend.dao.ObjectsDAO;
import ru.vsu.netcracker.parking.backend.json.EvacServiceJsonConverter;
import ru.vsu.netcracker.parking.backend.json.JsonConverter;
import ru.vsu.netcracker.parking.backend.models.Attributes;
import ru.vsu.netcracker.parking.backend.models.Obj;
import ru.vsu.netcracker.parking.backend.security.CustomAuthenticationProvider;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ObjService {

    private ObjectsDAO dao;
    private JsonConverter jsonConverter;
    private EvacServiceJsonConverter evacServiceJsonConverter;
    private CustomRestTemplate customRestTemplate;
    private Attributes attributes;

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public ObjService(ObjectsDAO objectsDAO, JsonConverter jsonConverter, Attributes attributes, EvacServiceJsonConverter evacServiceJsonConverter, CustomRestTemplate customRestTemplate) {
        this.dao = objectsDAO;
        this.jsonConverter = jsonConverter;
        this.evacServiceJsonConverter = evacServiceJsonConverter;
        this.attributes = attributes;
        this.customRestTemplate = customRestTemplate;
    }

    @PostConstruct
    public void init() {
        attributes.setAll(dao.getAttributes());
    }

    public Obj getObj(long objectId) {
        return dao.getObj(objectId);
    }


    public Obj saveObj(Obj obj) {
        return dao.saveObj(obj);
    }

    public JsonNode saveObjJson(JsonNode jsonNode) {
        Obj obj = dao.saveObj(jsonConverter.jsonToObject(jsonNode));
        return dao.getObjAsJSON(obj.getId());
    }

    public void deleteObj(long objectId) {
        dao.delete(objectId);
    }

    public JsonNode getObjAsJson(long objectId) {
        return dao.getObjAsJSON(objectId);
    }

    public ArrayNode getAllObjAsJson(String objectType) {
        return dao.getAllObjAsJSON(objectType);
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public JsonNode getObjByUsernameAsJson(String username) {
        Obj obj = dao.getObjByUserName(username);
        return dao.getObjAsJSON(obj.getId());
    }

    /* Evacuation service */

    private final long EVAC_ORDER_ID_ATTR_ID = 330L;
    private final long EVAC_ORDER_STATUS_ATTR_ID = 331L;
    private final long OWNER_ID_ATTR_ID = 300L;

    public JsonNode sendEvacRequest(long parkingId) {
        Obj parking = getObj(parkingId);
        Obj user = getObj(parking.getReferences().get(OWNER_ID_ATTR_ID));
        JsonNode jsonNode = evacServiceJsonConverter.createJsonRequest(user, parking);
        JsonNode jsonResponse = customRestTemplate.postForObject("customer/orders", jsonNode, JsonNode.class);
        long evacOrderId = jsonResponse.path("id").asLong();
        String statusOrder = jsonResponse.path("statusOrder").asText();
        parking.getValues().put(EVAC_ORDER_ID_ATTR_ID, String.valueOf(evacOrderId));
        parking.getValues().put(EVAC_ORDER_STATUS_ATTR_ID, statusOrder);
        saveObj(parking);
        return dao.getObjAsJSON(parking.getId());
    }

    private final long FREE_SPOTS_COUNT_ID = 307L;
    private final long STATUS_ID = 308L;

    public void updateEvacStatus(JsonNode jsonNode) {
        long evacOrderId = jsonNode.path("id").asLong();
        String statusOrder = jsonNode.path("statusOrder").asText();
        List<Obj> list = dao.getAllObj("Parking");
        Obj parking = list.stream()
                .filter(obj -> obj.getValues().get(EVAC_ORDER_ID_ATTR_ID) != null)
                .filter(obj -> Long.valueOf(obj.getValues().get(EVAC_ORDER_ID_ATTR_ID)) == evacOrderId)
                .findFirst().get();
        String currentStatus = parking.getListValues().get(STATUS_ID);
        if (currentStatus.equals("Completed")) {
            System.out.println("Order has already been completed. Doing nothing");
        } else {

            if (statusOrder.equals("Completed")) {
                parking.getValues().put(FREE_SPOTS_COUNT_ID, String.valueOf(1));
                parking.getListValues().put(STATUS_ID, "Free");

                // это, куда мы эвакуируем
                Obj evacToParking = getObj(EvacServiceJsonConverter.EVAC_TO_PARKING_ID);
                long freeSpotsCount = Long.valueOf(evacToParking.getValues().get(FREE_SPOTS_COUNT_ID));
                if (freeSpotsCount > 0) {
                    freeSpotsCount = freeSpotsCount - 1;
                    evacToParking.getValues().put(FREE_SPOTS_COUNT_ID, String.valueOf(freeSpotsCount));
                    if (freeSpotsCount == 0) {
                        evacToParking.getListValues().put(STATUS_ID, "Occupied");
                    }
                } else {
                    evacToParking.getValues().put(FREE_SPOTS_COUNT_ID, String.valueOf(10));
                    evacToParking.getListValues().put(STATUS_ID, "Free");
                }
                saveObj(evacToParking);
            }
            parking.getValues().put(EVAC_ORDER_STATUS_ATTR_ID, statusOrder);
            saveObj(parking);
        }
    }
}
