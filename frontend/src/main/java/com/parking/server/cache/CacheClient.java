package com.parking.server.cache;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.parking.server.objects.Pojo;
import com.parking.server.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;
import java.util.*;

@EnableCaching
public class CacheClient {

    @Autowired
    private ClientConfig hazelCastConfig;

    private HazelcastInstance instance;

    //@Autowired
    private Parser parser = new Parser();

    public CacheClient() {
        instance = HazelcastClient.newHazelcastClient(hazelCastConfig);
    }

    public HazelcastInstance getInstance() {
        return instance;
    }

    public Pojo getById(Long id) {
        IMap<Long, String> map = instance.getMap("parking");
        Pojo pojo = null;

        Set<Map.Entry<Long, String>> map_json = map.entrySet();
        for (Iterator<Map.Entry<Long, String>> iterator = map_json.iterator(); iterator.hasNext(); ) {
            Map.Entry<Long, String> entry = (Map.Entry<Long, String>) iterator.next();

            try {
                JsonNode jsonNode = new ObjectMapper().configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false)
                        .readValue(entry.getValue(), JsonNode.class);
                Pojo p = parser.read_json(jsonNode);
                if (p.getId() == id) {
                    pojo = p;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pojo;
    }

    public List<Pojo> getAll() {
        List<Pojo> list = new LinkedList<>();
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
            list.add(parser.read_json(jsonNode));
        }
        return list;
    }

    public List<JsonNode> getAll_json() {
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

    public void add(Pojo p) {
        JsonNode jsonNode = parser.write_json(p);
        try {
            instance.getMap("parking").put(jsonNode.get("id").asLong(),
                    new ObjectMapper().configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false)
                            .writeValueAsString(jsonNode));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void update(Pojo new_pojo) {
        IMap<Long, String> map = instance.getMap("parking");
        Set<Map.Entry<Long, String>> map_json = map.entrySet();
        for (Iterator<Map.Entry<Long, String>> iterator = map_json.iterator(); iterator.hasNext(); ) {
            Map.Entry<Long, String> entry = (Map.Entry<Long, String>) iterator.next();

            try {
                JsonNode jsonNode = new ObjectMapper().configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false)
                        .readValue(entry.getValue(), JsonNode.class);
                Pojo p = parser.read_json(jsonNode);
                if (p.getId() == new_pojo.getId()) {
                    JsonNode new_json = parser.write_json(new_pojo);
                    map.replace(p.getId(), new ObjectMapper().configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false)
                            .writeValueAsString(new_json));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //не удаляет, проблема именно в Map, хз, как из неё удалить
    public void delete(Long id) {
        IMap<Long, String> map = instance.getMap("parking");
        Set<Map.Entry<Long, String>> map_json = map.entrySet();
        for (Iterator<Map.Entry<Long, String>> iterator = map_json.iterator(); iterator.hasNext(); ) {
            Map.Entry<Long, String> entry = (Map.Entry<Long, String>) iterator.next();

            try {
                JsonNode jsonNode = new ObjectMapper().configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false)
                        .readValue(entry.getValue(), JsonNode.class);
                Pojo p = parser.read_json(jsonNode);
                if (p.getId() == id) {
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
