package ru.vsu.netcracker.parking.backend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.netcracker.parking.backend.models.Attributes;
import ru.vsu.netcracker.parking.backend.services.ObjService;

import javax.xml.ws.Response;
import java.util.Map;

@RestController
@RequestMapping(value = "/restapi")
class RestObjController {

    private ObjService objService;

    @Autowired
    public RestObjController(ObjService objService) {
        this.objService = objService;
    }

    @GetMapping(value = "/objects/{objectId}", produces = "application/json")
    public JsonNode getObjAsJson(@PathVariable long objectId) {
        return objService.getObjAsJson(objectId);
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ArrayNode getAllUsersAsJson() {
        return objService.getAllObjAsJson("User");
    }

    @PostMapping(value = "/users/get-user", consumes = "application/json", produces = "application/json")
    public JsonNode getUserByUsernameAsJson(@RequestBody String username) {
        return objService.getObjByUsernameAsJson(username);
    }

    @GetMapping(value = "/parkings", produces = "application/json")
    public ArrayNode getAllParkingsAsJson() {
        return objService.getAllObjAsJson("Parking");
    }

    @GetMapping(value = "/attributes", produces = "application/json")
    public Attributes getAttrubutesAsJson() {
        return objService.getAttributes();
    }

    @PutMapping(value = "/objects/{objectId}", consumes = "application/json")
    public void updateObj(@RequestBody JsonNode jsonNode, @PathVariable long objectId) {
        objService.saveObjJson(jsonNode);
    }

    @PostMapping(value = "/objects", consumes = "application/json", produces = "application/json")
    public JsonNode createObj(@RequestBody JsonNode jsonNode) {
        return objService.saveObjJson(jsonNode);
    }

    @DeleteMapping(value = "/objects/{objectId}")
    public void deleteObj(@PathVariable long objectId) {
        objService.deleteObj(objectId);
    }


}
