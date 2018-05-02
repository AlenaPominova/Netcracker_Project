package com.parking.server.cache;

import com.fasterxml.jackson.databind.JsonNode;
import com.parking.server.config.DataBaseConfig;
import com.parking.server.dao.JDBCDao;
import com.parking.server.dao.PersistException;
import com.parking.server.objects.Pojo;
import com.parking.server.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@EnableCaching
public class ParkingCache implements IParkingCache {

    JDBCDao dao;

    {
        try {
            dao = new JDBCDao(new DataBaseConfig().getContext());
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(Pojo pojo) throws IOException, PersistException {
        dao.create(pojo);
    }

    @Override
    public void update(Pojo pojo) throws IOException, PersistException {
        dao.update(pojo);
    }

    @Override
    public void delete(Long id) throws PersistException {
        dao.delete(id);
    }

    @Override
    public List<JsonNode> load_all() {
        List<JsonNode> list = null;
        try {
            list = dao.get_parking();
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return list;
    }
}
