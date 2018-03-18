package ru.NC.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.NC.service.impl.ParkingService;

import java.io.IOException;
import java.net.URL;

@Controller
@RequestMapping("/object")
public class HomeController {
    @Autowired
    private ParkingService parkingService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String findById(@PathVariable Integer id, ModelMap model) throws IOException {
        model.addAttribute("object", parkingService.getById(id));
        return "objectInfo";
    }

    @RequestMapping(value = {"/map", "/"}, method = RequestMethod.GET)
    public String find(ModelMap model) throws IOException {
        return "test";
    }
}
