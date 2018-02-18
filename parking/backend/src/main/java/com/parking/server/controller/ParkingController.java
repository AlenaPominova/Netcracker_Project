package com.parking.server.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.parking.server.dao.PersistException;
import com.parking.server.objects.Pojo;
import com.parking.server.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ObjectService service;

    @RequestMapping(value = "/objects", method = RequestMethod.GET)
    @ResponseBody
    public List<Pojo> getAll() throws PersistException {
        return service.getAll();
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
}