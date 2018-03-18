package ru.NC.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.NC.models.Obj;

public interface IObjService {
    Obj getObjById(final int id);
    void create(Obj object);
    void update(Obj object);
    void delete(Obj object);
    ObjectNode getObjectAsJson(long id);
    ArrayNode getAllObjectsAsJson(String objectType);
}
