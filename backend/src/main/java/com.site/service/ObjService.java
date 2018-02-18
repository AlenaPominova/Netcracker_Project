package com.site.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.site.dao.IObjDAO;
import com.site.objects.Pojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjService implements IObjService {

    @Autowired
    public IObjDAO dao;

    public Pojo read(long id) {
        return dao.read(id);
    }
    public JsonNode getJson(long id){
        return dao.getJson(id);
    }

    @Override
    public JsonNode deleteById(long id) {
        return dao.delete(id);
    }

    @Override
    public JsonNode create(Pojo obj) {
        return dao.create(obj);
    }
}
