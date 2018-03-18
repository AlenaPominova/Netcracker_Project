package ru.NC.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import ru.NC.dao.ObjectDaoImpl;
import ru.NC.models.Obj;

import java.util.ArrayList;
import java.util.List;

public class ObjService implements IObjService{
    @Autowired
    private ObjectDaoImpl objectDao;

    @Override
    public Obj getObjById(int id) {
        return objectDao.read(id);
    }

    @Override
    public void create(Obj object) {
        objectDao.create(object);
    }

    @Override
    public void update(Obj object) {
        objectDao.update(object);
    }

    @Override
    public void delete(Obj object) {
        objectDao.delete(object.getId());
    }

    @Override
    public ObjectNode getObjectAsJson(long id) {
        return objectDao.getObjectAsJson(id);
    }

    @Override
    public ArrayNode getAllObjectsAsJson(String objectType) {
        return objectDao.getAllObjectsAsJson(objectType);
    }
}
