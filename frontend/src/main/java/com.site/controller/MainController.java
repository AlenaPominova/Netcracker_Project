package com.site.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.entity.Pojo;
import com.site.json.JsonParser;
import com.site.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

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

    @GetMapping("/index")
    public String index(Model model){
        return "main";
    }

}
