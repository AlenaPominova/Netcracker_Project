package com.parking.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.parking.server.dao.PersistException;
import com.parking.server.objects.Pojo;
import org.springframework.cache.annotation.CachePut;

import java.io.IOException;
import java.util.List;

public interface ObjectService {

    void create(JsonNode jsonNode) throws PersistException, IOException;

    Pojo getByPK(Long id)throws PersistException;

    void update(JsonNode jsonNode) throws PersistException, IOException;

    void delete(Long id)throws PersistException;

    List<Pojo> getAll()throws PersistException;

    List<JsonNode> get_parking() throws PersistException;

    List<Pojo> get_parking_pojo() throws PersistException;

    JsonNode writeJson(Long id) throws PersistException, IOException;

    List<JsonNode> writeAllJsons() throws PersistException, IOException;
}
