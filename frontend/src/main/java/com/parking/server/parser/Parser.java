package com.parking.server.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.parking.server.objects.Pojo;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    public Pojo read_json(JsonNode rootNode) {

        //Это если принимать не JsonNode, а String
        //String s = jsonNode.asText();
        //ObjectMapper mapper = new ObjectMapper();
        //JsonNode rootNode = mapper.readValue(s, JsonNode.class);

        Pojo p = new Pojo(rootNode.get("id").asLong(),
                rootNode.get("name").asText(),
                rootNode.get("type_id").asLong());

        ArrayNode values = (ArrayNode) rootNode.get("values");
        ArrayNode list_values = (ArrayNode) rootNode.get("list_values");
        ArrayNode dates = (ArrayNode) rootNode.get("date");
        ArrayNode references = (ArrayNode) rootNode.get("references");

        Map<Long, String> map_value = new HashMap<>();
        Map<Long, Long> map_list_value = new HashMap<>();
        Map<Long, Timestamp> map_date = new HashMap<>();
        Map<Long, Long> map_reference = new HashMap<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if (values != null) {
            for (JsonNode cur : values) {
                map_value.put(cur.get("id").asLong(), cur.get("value").asText());
            }
        }
        p.setValues(map_value);
        if (list_values != null) {
            for (JsonNode cur : list_values) {
                map_list_value.put(cur.get("id").asLong(), cur.get("value").asLong());
            }
        }
        p.setListValue(map_list_value);
        if (dates != null) {
            for (JsonNode cur : dates) {
                String target = cur.get("value").asText();
                Date date = null;
                try {
                    date = format.parse(target);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                map_date.put(cur.get("id").asLong(), new Timestamp(date.getTime()));
            }
        }
        p.setDate(map_date);
        if (references != null) {
            for (JsonNode cur : references) {
                map_reference.put(cur.get("id").asLong(), cur.get("value").asLong());
            }
        }
        p.setReferences(map_reference);

        return p;
    }

    public JsonNode write_json(Pojo p) {

        JsonNodeFactory factory = new JsonNodeFactory(false);
        JsonNode object = factory.objectNode();

        ((ObjectNode) object).put("id", p.getId());
        ((ObjectNode) object).put("name", p.getName());
        ((ObjectNode) object).put("type_id", p.getType_id());

        //Map<Long, String> map = getNameAttr();

        ArrayNode values = factory.arrayNode();
        for (Map.Entry entry : p.getValues().entrySet()) {
            ObjectNode value = factory.objectNode();
            value.put("id", entry.getKey().toString());
            //value.put("name", map.get(entry.getKey()));
            value.put("value", entry.getValue().toString());
            values.add(value);
        }
        ArrayNode list_values = factory.arrayNode();
        for (Map.Entry entry : p.getListValue().entrySet()) {
            ObjectNode value = factory.objectNode();
            value.put("id", entry.getKey().toString());
            value.put("value", entry.getValue().toString());
            list_values.add(value);
        }
        ArrayNode dates = factory.arrayNode();
        for (Map.Entry entry : p.getDate().entrySet()) {
            ObjectNode value = factory.objectNode();
            value.put("id", entry.getKey().toString());
            //value.put("name", map.get(entry.getKey()));
            value.put("value", entry.getValue().toString());
            dates.add(value);
        }
        ArrayNode references = factory.arrayNode();
        for (Map.Entry entry : p.getReferences().entrySet()) {
            ObjectNode value = factory.objectNode();
            value.put("id", entry.getKey().toString());
            value.put("value", entry.getValue().toString());
            references.add(value);
        }
        ((ObjectNode) object).put("values", values);
        ((ObjectNode) object).put("date", dates);
        ((ObjectNode) object).put("list_values", list_values);
        ((ObjectNode) object).put("references", references);

        return object;
    }

    /*public Map<Long, String> getNameAttr() throws PersistException {
        Map<Long, String> map = new HashMap<>();
        String sql = sqlObjectDao.getNameAttr();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                map.put(rs.getLong("attr_id"), rs.getString("name"));
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return map;
    }*/
}
