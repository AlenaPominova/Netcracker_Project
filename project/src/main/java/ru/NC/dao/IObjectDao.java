package ru.NC.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.NC.models.Obj;

public interface IObjectDao {

    Obj read(final int id);
    void create(Obj object);
    void update(Obj object);
    void delete(int id);
    ObjectNode getObjectAsJson(long id);
}
