package ru.NC.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.NC.service.IObjService;

@RestController
@RequestMapping("/backend")
public class BackendController {
    @Autowired
    private IObjService objectService;

    @RequestMapping(value = "/object/{id}", method = RequestMethod.GET)
    public JsonNode getObject(@PathVariable Long id){
        return objectService.getObjectAsJson(id);
    }

    @RequestMapping(value = "/objects/type/{type}", method = RequestMethod.GET)
    public ArrayNode getObjects(@PathVariable String type){
        return objectService.getAllObjectsAsJson(type);
    }
}
