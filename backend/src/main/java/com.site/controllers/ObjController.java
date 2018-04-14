package com.site.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.site.json.JsonParser;
import com.site.objects.Pojo;
import com.site.service.IObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/")
public class ObjController {

    @Autowired
    public IObjService objService;

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonNode getJSON(@PathVariable long id){
        return objService.getJson(id);
    }

    @RequestMapping(value = "/getByObjectType/{objectType}", method = RequestMethod.GET)
    @ResponseBody
    public JsonNode getJSONbyObjectType(@PathVariable String objectType) {
        return objService.getByObjectType(objectType);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public JsonNode deleteObject(@RequestBody JsonNode node){
        return objService.deleteById(node.get("id").asInt());
    }

    @RequestMapping(value = "/take/{id}", method = RequestMethod.GET)
    public ModelAndView profile(@PathVariable long id){
        ModelAndView model = new ModelAndView("profile");
        JsonParser parser = new JsonParser();
        JsonNode node = getJSON(id);

        model.addObject("id", node.get("id").asText());
        model.addObject("name", node.get("name").asText());
        model.addObject("type_id", node.get("type_id").asText());
        Map<String, String> map = parser.getValues(node.get("values"));
        model.addObject("data", map);

        return model;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public JsonNode createObject(@PathVariable Pojo pojo){
        Pojo temp = objService.read(5);
        temp.setId(79);
        objService.create(temp);
        return null;
    }

}
