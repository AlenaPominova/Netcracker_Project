package ru.NC.dao;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.NC.models.Obj;

public interface IObjectDao {

    Obj read(final int id);
    void create(Obj object);
    void update(Obj object);
    void delete(long id);
    ObjectNode getObjectAsJson(long id);
    ArrayNode getAllObjectsAsJson(String objectType);
}
