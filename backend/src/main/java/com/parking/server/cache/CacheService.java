package com.parking.server.cache;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.parking.server.dao.PersistException;
import com.parking.server.objects.Pojo;
import com.parking.server.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@EnableCaching
public class CacheService {

    @Autowired
    private Config hazelCastConfig;

    //@Autowired
    //private IParkingCache parkingCache = new ParkingCache();

    private HazelcastInstance instance;

    public CacheService() {
        instance = Hazelcast.newHazelcastInstance(hazelCastConfig);
    }

    public void load(List<JsonNode> parking) {
        for (JsonNode jsonNode : parking) {
            try {
                instance.getMap("parking").put(jsonNode.get("id").asLong(),
                        new ObjectMapper().configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false)
                                .writeValueAsString(jsonNode));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public List<JsonNode> getAll() {
        List<JsonNode> list = new LinkedList<>();
        IMap<Long, String> map = instance.getMap("parking");
        JsonNode jsonNode = null;

        Set<Map.Entry<Long, String>> map_json = map.entrySet();
        for (Iterator<Map.Entry<Long, String>> iterator = map_json.iterator(); iterator.hasNext(); ) {
            Map.Entry<Long, String> entry = (Map.Entry<Long, String>) iterator.next();

            try {
                jsonNode = new ObjectMapper().configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false)
                        .readValue(entry.getValue(), JsonNode.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(jsonNode);
        }
        return list;
    }

   /* public void load_pojo(List<Pojo> parking_pojo) {
        for (Pojo pojo : parking_pojo) {
            instance.getMap("parking").put(pojo.getId(), pojo);
        }
    }*/
}
