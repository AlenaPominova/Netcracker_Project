package ru.vsu.netcracker.parking.backend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.netcracker.parking.backend.services.ObjService;

import java.util.Map;

@RestController
@RequestMapping(value = "/restapi")
class RestObjController {

    private ObjService objService;

    @Autowired
    public RestObjController(ObjService objService) {
        this.objService = objService;
    }

    @GetMapping(value = "/{objectId}", produces = "application/json")
    public JsonNode getObjAsJson(@PathVariable long objectId) {
        return objService.getObjAsJson(objectId);
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ArrayNode getAllUsersAsJson() {
        return objService.getAllObjAsJson("User");
    }

    @GetMapping(value = "/parkings", produces = "application/json")
    public ArrayNode getAllParkingsAsJson() {
        return objService.getAllObjAsJson("Parking");
    }

    @GetMapping(value = "/attributes", produces = "application/json")
    public Map<Long, String> getAttrubutesAsJson() {
        return objService.getAttributes().getAll();
    }

    @PutMapping(value = "/{objectId}", consumes = "application/json")
    public void saveObj(@RequestBody JsonNode jsonNode) {
        objService.saveObjJson(jsonNode);
    }

    @DeleteMapping(value = "/{objectId}")
    public void deleteObj(@PathVariable long objectId) {
        objService.deleteObj(objectId);
    }

    @PostMapping(value = "/get-user", consumes = "application/json", produces = "application/json")
    public JsonNode getObjByUsernameAsJson(@RequestBody String username) {
        JsonNode jsonNode = objService.getObjByUsernameAsJson(username);
        return jsonNode;
    }
}
