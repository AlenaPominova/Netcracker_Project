package com.site.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.entity.Pojo;
import com.site.json.JsonParser;
import com.site.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.net.URL;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/home")
    @ResponseBody
    public Pojo hello() throws IOException {
        String url = "http://localhost:8081/profile/5";
        ObjectMapper objectMapper = new ObjectMapper();
        URL url1 = new URL(url);
        return JsonParser.getObj(objectMapper.readValue(url1, JsonNode.class));
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        return "reg";
    }

    @GetMapping("/index")
    public String index(Model model){
        return "main";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle(Exception ex) {
        return "redirect:/404";
    }

    @RequestMapping(value = {"/404"}, method = RequestMethod.GET)
    public String NotFoudPage() {
        return "404";
    }

}
