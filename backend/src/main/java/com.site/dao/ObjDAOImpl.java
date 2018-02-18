package com.site.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.site.json.JsonCreater;
import com.site.mapper.ObjMapper;
import com.site.objects.Pojo;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.site.sql.SQLConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


public class ObjDAOImpl implements IObjDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ObjDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public JsonNode create(Pojo obj) {
        jdbcTemplate.update("INSERT INTO \"OBJECTS\" (object_id, object_type_id, name, parent_id) VALUES (?, ?, ?, ?)", obj.getId(), obj.getType_id(), obj.getName(), null);
        for (Map.Entry entry: obj.getValues().entrySet()){
            jdbcTemplate.update("INSERT INTO \"PARAMS\" (attr_id, object_id, value, date_value, list_value_id) VALUES (?, ?, ?, null, null)", entry.getKey(), obj.getId(), entry.getValue());
        }
        for (Map.Entry entry: obj.getListValue().entrySet()){
            jdbcTemplate.update("INSERT INTO \"PARAMS\" (attr_id, object_id, value, date_value, list_value_id) VALUES (?, ?, null, null, ?)", entry.getKey(), obj.getId(), entry.getValue());
        }
        for (Map.Entry entry: obj.getDate().entrySet()){
            jdbcTemplate.update("INSERT INTO \"PARAMS\" (attr_id, object_id, value, date_value, list_value_id) VALUES (?, ?, null, ?, null)", entry.getKey(), obj.getId(), entry.getValue());
        }
        return null;
    }

    public Pojo read(long id) {
        Pojo temp = jdbcTemplate.queryForObject(SQLConstant.GET_INFO_BY_ID, new ObjMapper(), id);
        temp.setValues(jdbcTemplate.query(SQLConstant.GET_VALUES_BY_ID, (resultSet) -> {
            Map<Long, String> map = new HashMap<>();
            while (resultSet.next()){
                if (resultSet.getString("value") != null)
                    map.put(resultSet.getLong("attr_id"), resultSet.getString("value"));
            }
            return map;
        }, temp.getId(), temp.getType_id()));

        temp.setListValue(jdbcTemplate.query(SQLConstant.GET_VALUES_BY_ID, (resultSet) -> {
            Map<Long, String> map = new HashMap<>();
            while (resultSet.next()){
                if (resultSet.getString("list_value_id") != null)
                    map.put(resultSet.getLong("attr_id"), resultSet.getString("list_value_id"));
            }
            return map;
        }, temp.getId(), temp.getType_id()));

        temp.setDate(jdbcTemplate.query(SQLConstant.GET_VALUES_BY_ID, (resultSet) -> {
            Map<Long, Timestamp> map = new HashMap<>();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            while (resultSet.next()){
                if (resultSet.getString("date_value") != null){
                    String target = resultSet.getString("date_value");
                    Date result;
                    try {
                        result =  format.parse(target);
                    } catch (ParseException e) {
                        result = null;
                        e.printStackTrace();
                    }
                    map.put(resultSet.getLong("attr_id"), new Timestamp(result.getTime()));
                }
            }
            return map;
        }, temp.getId(), temp.getType_id()));
        return temp;
    }

    @Override
    public JsonNode update(Pojo obj) {
        return null;
    }

    @Override
    public JsonNode delete(long id) {
        int val = jdbcTemplate.update(SQLConstant.DELETE_BY_ID, id);
        if (val == 1)
            return JsonCreater.isDeleted("Successful");
        else
            return JsonCreater.isDeleted("Invalid");
    }

    public JsonNode getJson(long id){
        Pojo obj = read(id);
        Map<Long, String> attrName = jdbcTemplate.query(SQLConstant.GET_VALUES_BY_ID, (resultSet) -> {
            Map<Long, String> map = new HashMap<>();
            while (resultSet.next()){
                map.put(resultSet.getLong("attr_id"), resultSet.getString("name"));
            }
            return map;
        }, id, obj.getType_id());
        return JsonCreater.getJson(obj, attrName);
    }
}
