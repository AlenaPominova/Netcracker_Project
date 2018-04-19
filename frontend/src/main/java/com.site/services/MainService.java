package com.site.services;

import com.site.entity.Pojo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.*;

@Service
public class MainService {

    public Pojo getPojo(){
        Map<String, String> val = new HashMap<String, String>();
        val.put("login","believedream95@gmail.com");
        val.put("password","gendos1337");
        return new Pojo(1,"TestUser", 2, 0, val);
    }
}
