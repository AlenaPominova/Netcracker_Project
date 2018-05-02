package com.parking.server.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.server.objects.Pojo;
import com.parking.server.parser.Parser;
import com.sun.xml.internal.ws.server.ServerRtException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    Parser parser = new Parser();

    @Override
    public Pojo getUser(String login) {//}, String password) {
        Pojo p = null;
        String url = "http://localhost:8082/parking/objects_json";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<JsonNode> list = objectMapper.readValue(new URL(url), new TypeReference<List<JsonNode>>() {
            });
            for (JsonNode jsonNode : list) {
                Pojo cur = parser.read_json(jsonNode);
                if (cur.getType_id() == 2) {
                    if (cur.getValues().containsValue(login)){// && cur.getValues().containsValue(password)) {
                        p = cur;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }
}
