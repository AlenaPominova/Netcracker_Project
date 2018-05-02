package com.parking.server.cache;

import com.fasterxml.jackson.databind.JsonNode;
import com.parking.server.dao.PersistException;
import com.parking.server.objects.Pojo;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IParkingCache {

    @CachePut("parking") //выполняется каждый раз для id
    public void put(Pojo pojo) throws IOException, PersistException;

    @CachePut("parking")
    public void update(Pojo pojo) throws IOException, PersistException;

    @CachePut("parking")
    public void delete(Long id) throws PersistException;

    @Cacheable("parking") //выполняется 1 раз до очистки кэша
    public List<JsonNode> load_all();
}
