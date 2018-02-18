package com.site.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.site.entity.Pojo;

import java.util.HashMap;
import java.util.Map;

public class JsonParser {

    public static Pojo getObj(JsonNode jsonNode){
        return new Pojo(jsonNode.get("id").longValue(),
                        jsonNode.get("name").textValue(),
                        jsonNode.get("type_id").longValue(),
                        jsonNode.get("parent_id").longValue(),
                        getValues(jsonNode.get("values")));
    }

    private static Map<String, String> getValues(JsonNode jsonNode){
        Map<String, String> temp = new HashMap<String, String>();
        for (int i = 0; i < jsonNode.size(); i++){
            temp.put(jsonNode.get(i).get("name").textValue(), jsonNode.get(i).get("value").textValue());
        }
        return temp;
    }
}
