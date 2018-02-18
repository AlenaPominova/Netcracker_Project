package com.parking.server.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.parking.server.objects.Pojo;

import java.io.IOException;
import java.util.List;


public interface GenericDao {

    public void create(JsonNode jsonNode) throws PersistException, IOException;

    public Pojo getByPK(Long id) throws PersistException;

    public void update(JsonNode jsonNode) throws PersistException, IOException;

    public void delete(Long id) throws PersistException;

    public List<Pojo> getAll() throws PersistException;

    public JsonNode writeJson(Long id) throws IOException, PersistException;
}
