package com.parking.server.read_json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.parking.server.dao.JDBCDao;
import com.parking.server.objects.Pojo;
import com.parking.server.sql.SqlObjectDao;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ReadJson {

    final SqlObjectDao sqlObjectDao = new SqlObjectDao();

    /*
    * Настенька, поменять serial на bigint в бд, чтобы формат до минут/секунд
    * */
    public Long getNewObject_id() {
        Random rnd = new Random(System.currentTimeMillis());
        //String curDate = new SimpleDateFormat("ddMMyyyyHHmmss").format(System.currentTimeMillis());
        String curDate = new SimpleDateFormat("ddMMyyHH").format(System.currentTimeMillis());
        return Long.valueOf(curDate)+rnd.nextInt();
    }

    /*
    * Настенька, зн-е references мб тоже генерить, а не из jsonа вытаскивать, а
    * */
    public String readJsonForCreate(JsonNode rootNode) throws IOException {

        String create_object_query = sqlObjectDao.getCreateQuery();
        String insert_params_query = sqlObjectDao.getInsertQuery();
        String create_references_query = sqlObjectDao.getCreateReferencesQuery();

        //Это если принимать не JsonNode, а String
        //String s = jsonNode.asText();
        //ObjectMapper mapper = new ObjectMapper();
        //JsonNode rootNode = mapper.readValue(s, JsonNode.class);

        Long object_id = getNewObject_id();
        String name = "User" + object_id;
        Long type_id = rootNode.get("type_id").asLong();
        create_object_query += "(" + object_id + "," + type_id + ", '" + name + "' ); ";

        ArrayNode values = (ArrayNode) rootNode.get("values");
        ArrayNode list_values = (ArrayNode) rootNode.get("list_values");
        ArrayNode dates = (ArrayNode) rootNode.get("date");
        ArrayNode references = (ArrayNode) rootNode.get("references");

        if (values != null) {
            for (JsonNode cur : values) {
                insert_params_query += "(" + cur.get("id").asText() + ", " + object_id + ", '" + cur.get("value").asText() + "', " + "null, " + "null), ";
            }
        }
        if (list_values != null) {
            for (JsonNode cur : list_values) {
                insert_params_query += "(" + cur.get("id").asText() + ", " + object_id + ", " + "null, " + "null, '" + cur.get("value").asText() + "'), ";
            }
        }
        if (dates != null) {
            for (JsonNode cur : dates) {
                insert_params_query += "(" + cur.get("id").asText() + ", " + object_id + ", " + "null" + ", '" + cur.get("value").asText() + "', " + "null), ";
            }
        }
        for (JsonNode cur : references) {
            create_references_query += "(" + cur.get("id").asText() + ", " + cur.get("value").asText() + "," + object_id + ");";
        }

        String general_create_query = create_object_query +
                insert_params_query.substring(0, insert_params_query.length() - 2) + "; " +
                create_references_query;

        return general_create_query;
    }

    public Pojo readJsonforUpdate(JsonNode rootNode) throws IOException {

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
        Map<Long, String> map_list_value = new HashMap<>();
        Map<Long, Timestamp> map_date = new HashMap<>();
        Map<Long, String> map_reference = new HashMap<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if (values != null) {
            for (JsonNode cur : values) {
                map_value.put(cur.get("id").asLong(), cur.get("value").asText());
            }
        }
        p.setValues(map_value);
        if (list_values != null) {
            for (JsonNode cur : list_values) {
                map_list_value.put(cur.get("id").asLong(), cur.get("value").asText());
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
                map_reference.put(cur.get("id").asLong(), cur.get("value").asText());
            }
        }
        p.setReferences(map_reference);

        return p;
    }
}
