package ru.vsu.netcracker.parking.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vsu.netcracker.parking.frontend.services.ObjService;

import java.util.Map;

@Controller
public class ObjController {

    ObjService objService;

    @Autowired
    public ObjController(ObjService objService) {
        this.objService = objService;
    }

    @GetMapping(value = "")
    public String main(Model model) {
        return "redirect:/parkings/";
    }
}
