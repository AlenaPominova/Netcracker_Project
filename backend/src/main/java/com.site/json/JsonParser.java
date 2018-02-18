package com.site.json;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

public class JsonParser {
    public Map<String, String> getValues(JsonNode jsonNode){
        Map<String, String> temp = new HashMap<String, String>();
        for (int i = 0; i < jsonNode.size(); i++){
            temp.put(jsonNode.get(i).get("name").textValue(), jsonNode.get(i).get("value").textValue());
        }
        return temp;
    }
}
