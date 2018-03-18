package ru.NC.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.NC.service.impl.ParkingService;
import ru.NC.service.impl.RoleService;
import ru.NC.service.impl.UserService;

import java.io.IOException;
import java.net.URL;

@Controller
public class FrontendController {
    @Autowired
    private UserService userService;
    @Autowired
    private ParkingService parkingService;
    @Autowired
    private RoleService roleService;

    public void getObjectById(long id) throws IOException {
        String url = "http://localhost:8080/backend/object/" + id;
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode obj = objectMapper.readValue(new URL(url), JsonNode.class);
    }

    @RequestMapping("loadObjects/{type}")
    public void getObjectsByType(@PathVariable String type) throws IOException {
        String url = "http://localhost:8080/backend/objects/type/" + type;
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode obj = objectMapper.readValue(new URL(url), ArrayNode.class);
        if (type.equals("User")){
            userService.fill(obj);
        } else {
            if (type.equals("Parking")){
                parkingService.fill(obj);
            } else
            {
                roleService.fill(obj);
            }
        }
    }
}
