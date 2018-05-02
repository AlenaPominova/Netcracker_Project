package com.parking.server.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.parking.server.objects.User;

import java.util.*;

public class JsonForUser extends JsonParser<User> {

    public List<User> parseAll(ArrayNode dataSet){
        List<User> users = new ArrayList<User>();
        Iterator datasetElements = dataSet.iterator();
        while (datasetElements.hasNext()) {
            JsonNode jsonUser = (JsonNode) datasetElements.next();
            User user = parseJson(jsonUser);
            users.add(user);
        }
        return users;
    }

    public User parseJson(JsonNode rootNode) {

        User user = new User(rootNode.get("id").asLong());
        ArrayNode values = (ArrayNode) rootNode.get("values");
        ArrayNode dates = (ArrayNode) rootNode.get("date");
        Map<Long, String> map_attr = new HashMap<>();

        if (values != null) {
            for (JsonNode cur : values) {
                map_attr.put(cur.get("id").asLong(), cur.get("name").asText());
                user = setAttribute(user, cur.get("name").asText(), cur.get("value").asText());
            }
        }
        if (dates != null) {
            for (JsonNode cur : dates) {
                map_attr.put(cur.get("id").asLong(), cur.get("name").asText());
                user = setAttribute(user, cur.get("name").asText(), cur.get("value").asText());
            }
        }
        user.setAttr_name(map_attr);

        return user;
    }
}
