package ru.vsu.netcracker.parking.backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.netcracker.parking.backend.dao.ObjectsDAO;
import ru.vsu.netcracker.parking.backend.json.JsonConverter;
import ru.vsu.netcracker.parking.backend.models.Attributes;
import ru.vsu.netcracker.parking.backend.models.Obj;
import ru.vsu.netcracker.parking.backend.security.CustomAuthenticationProvider;

import javax.annotation.PostConstruct;

@Service
public class ObjService {

    private ObjectsDAO dao;
    private JsonConverter jsonConverter;
    private Attributes attributes;

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public ObjService(ObjectsDAO objectsDAO, JsonConverter jsonConverter, Attributes attributes) {
        this.dao = objectsDAO;
        this.jsonConverter = jsonConverter;
        this.attributes = attributes;
    }

    @PostConstruct
    public void init() {
        attributes.setAll(dao.getAttributes());
    }

    public Obj getObj(long objectId) {
        return dao.getObj(objectId);
    }


    public void saveObj(Obj obj) {
        dao.saveObj(obj);
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
}
