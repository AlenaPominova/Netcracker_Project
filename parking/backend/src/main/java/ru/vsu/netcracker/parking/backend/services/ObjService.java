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

    public JsonNode sendEvacRequest(long parkingId) {
        Obj parking = getObj(parkingId);
        JsonNode jsonNode = evacServiceJsonConverter.createJsonRequest(parking);
        JsonNode jsonResponse = customRestTemplate.postForObject("vendor/orders", jsonNode, JsonNode.class);
        long id = jsonResponse.path("id").asLong();   //get evacOrderId from jsonResponse
//        long id = 125L; //for testing
        String statusOrder = "In progress";
//        String statusOrder = jsonResponse.path("statusOrder").asText();   //  Может не надо
        parking.getValues().put(EVAC_ORDER_ID_ATTR_ID, String.valueOf(id));
        parking.getValues().put(EVAC_ORDER_STATUS_ATTR_ID, statusOrder);
        saveObj(parking);
        return dao.getObjAsJSON(parking.getId());
    }

    private final long FREE_SPOTS_COUNT_ID = 307L;
    private final long STATUS_ID = 308L;

    public void updateEvacStatus(JsonNode jsonNode) {
        //long evacOrderId = evacServiceJsonConverter.parseJsonResponse(jsonNode);
        long evacOrderId = jsonNode.path("id").asLong();    //does same as row above
        List<Obj> list = dao.getAllObj("Parking");
        Obj parking = list.stream()
                .filter(obj -> obj.getValues().get(EVAC_ORDER_ID_ATTR_ID) != null)
                .filter(obj -> Long.valueOf(obj.getValues().get(EVAC_ORDER_ID_ATTR_ID)) == evacOrderId)
                .findFirst().get();
        parking.getValues().put(EVAC_ORDER_ID_ATTR_ID, String.valueOf(evacOrderId));
        parking.getValues().put(EVAC_ORDER_STATUS_ATTR_ID, "Completed");
        saveObj(parking);

        // это, куда мы эвакуируем
        Obj evacToParking = getObj(EvacServiceJsonConverter.EVAC_TO_PARKING_ID);
        long freeSpotsCount = Long.valueOf(parking.getValues().get(FREE_SPOTS_COUNT_ID));
        if (freeSpotsCount > 0) {
            evacToParking.getValues().put(FREE_SPOTS_COUNT_ID, String.valueOf(--freeSpotsCount));
            evacToParking.getListValues().put(STATUS_ID, "Occupied");
        } else {
            evacToParking.getValues().put(FREE_SPOTS_COUNT_ID, String.valueOf(10));
            evacToParking.getListValues().put(STATUS_ID, "Free");
        }
        saveObj(evacToParking);
    }

//    public void updateEvacStatus(long id) { //for testing
//        long evacOrderId = 125L;
//        List<Obj> list = dao.getAllObj("Parking");
//        Obj parking = list.stream()
//                .filter(obj -> obj.getValues().get(EVAC_ORDER_ID_ATTR_ID) != null)
//                .filter(obj -> Long.valueOf(obj.getValues().get(EVAC_ORDER_ID_ATTR_ID)) == evacOrderId)
//                .findFirst().get();
//        parking.getValues().put(EVAC_ORDER_ID_ATTR_ID, String.valueOf(evacOrderId));
//        parking.getValues().put(EVAC_ORDER_STATUS_ATTR_ID, "Completed");
//        saveObj(parking);
//    }


}
