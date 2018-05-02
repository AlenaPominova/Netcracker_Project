package com.parking.server.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.parking.server.cache.CacheService;
import com.parking.server.cache.IParkingCache;
import com.parking.server.dao.PersistException;
import com.parking.server.objects.Pojo;
import com.parking.server.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ObjectService service;

    //@Autowired
    private CacheService cacheService = new CacheService();

    @RequestMapping(value = "/load_cache", method = RequestMethod.GET)
    @ResponseBody
    public void load_cache() throws PersistException {
        cacheService.load(service.get_parking());
    }

    @RequestMapping(value = "/cache_json", method = RequestMethod.GET)
    @ResponseBody
    public JsonNode get() throws PersistException {

        JsonNodeFactory factory = new JsonNodeFactory(false);
        JsonNode jsonNode = factory.objectNode();

        ArrayNode values = factory.arrayNode();
        for (int i = 0; i < cacheService.getAll().size(); i++){
            //values.add(getJson((long)(objectId.get(i))));
            values.add(cacheService.getAll().get(i));
        }
        ((ObjectNode) jsonNode).put("parkings", values);
        return jsonNode;
        //return cacheService.getAll();
    }

   /* @RequestMapping(value = "/load_cache_pojo", method = RequestMethod.GET)
    @ResponseBody
    public void load_cache_pojo() throws PersistException {
        //cacheService.load_cache();
        cacheService.load_pojo(service.get_parking_pojo());
    }*/

    @RequestMapping(value = "/objects", method = RequestMethod.GET)
    @ResponseBody
    public List<Pojo> getAll() throws PersistException {
        return service.getAll();
    }

    @RequestMapping(value = "/parking", method = RequestMethod.GET)
    @ResponseBody
    public List<JsonNode> get_parking() throws PersistException {
        return service.get_parking();
    }

    @RequestMapping(value = "/objects/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Pojo getPojo(@PathVariable("id") long id) throws PersistException {
        return service.getByPK(Long.valueOf(id));
    }

    @RequestMapping(value = "/objects", method = RequestMethod.POST)
    @ResponseBody
    public void create(@RequestBody JsonNode jsonNode) throws PersistException, IOException {
        service.create(jsonNode);
    }

    @RequestMapping(value = "/objects/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void delete(@PathVariable("id") long id) throws PersistException {
        service.delete(id);
    }

    @RequestMapping(value = "/objects_json/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonNode writeJson(@PathVariable("id") long id) throws PersistException, IOException {
        return service.writeJson(Long.valueOf(id));
    }

    @RequestMapping(value = "/objects_json", method = RequestMethod.GET)
    @ResponseBody
    public List<JsonNode> writeAllJsons() throws IOException, PersistException {

        return service.writeAllJsons();
    }
}