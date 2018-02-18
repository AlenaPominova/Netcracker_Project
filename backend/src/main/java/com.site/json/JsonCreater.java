package com.site.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.site.objects.Pojo;
import java.util.Map;

public class JsonCreater {

    public static JsonNode getJson(Pojo object, Map<Long, String> attrName){

        JsonNodeFactory factory = new JsonNodeFactory(false);
        JsonNode obj = factory.objectNode();

        ((ObjectNode) obj).put("id", object.getId());
        ((ObjectNode) obj).put("name", object.getName());
        ((ObjectNode) obj).put("type_id", object.getType_id());
        ((ObjectNode) obj).put("parent_id", object.getParent_id());

        ArrayNode values = factory.arrayNode();
        for (Map.Entry entry: object.getValues().entrySet()) {
            ObjectNode value = factory.objectNode();
            value.put("id", entry.getKey().toString());
            value.put("value", entry.getValue().toString());
            value.put("name", attrName.get(entry.getKey()).toString());
            values.add(value);
        }

        ArrayNode dates = factory.arrayNode();
        for (Map.Entry entry: object.getDate().entrySet()) {
            ObjectNode value = factory.objectNode();
            value.put("id", entry.getKey().toString());
            value.put("value", entry.getValue().toString());
            value.put("name", attrName.get(entry.getKey()).toString());
            dates.add(value);
        }

        ((ObjectNode) obj).put("values", values);
        ((ObjectNode) obj).put("date", dates);

        return obj;
    }
    public static JsonNode isDeleted(String answer){
        JsonNodeFactory factory = new JsonNodeFactory(false);
        JsonNode obj = factory.objectNode();
        ((ObjectNode) obj).put("value", answer);
        return obj;
    }
}
