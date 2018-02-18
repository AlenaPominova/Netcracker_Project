package com.parking.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.parking.server.config.DataBaseConfig;
import com.parking.server.dao.JDBCDao;
import com.parking.server.dao.PersistException;
import com.parking.server.objects.Pojo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ObjectServiceImpl implements ObjectService {

    JDBCDao dao;
    {
        try {
            dao = new JDBCDao(new DataBaseConfig().getContext());
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(JsonNode jsonNode) throws PersistException, IOException{
        dao.create(jsonNode);
    }

    @Override
    public Pojo getByPK(Long id) throws PersistException {
        return dao.getByPK(id);
    }

    @Override
    public void update(JsonNode jsonNode) throws PersistException, IOException {
        dao.update(jsonNode);
    }

    @Override
    public void delete(Long id) throws PersistException {
        dao.delete(id);
    }

    @Override
    public List<Pojo> getAll() throws PersistException {
        return dao.getAll();
    }

    @Override
    public JsonNode writeJson(Long id) throws PersistException, IOException {
        return dao.writeJson(id);
    }
}
