package com.parking.server.objects;

import java.sql.Timestamp;
import java.util.Map;

public class Pojo {

    private long id;
    private String name;
    private long type_id;
    private Map<Long, String> values;
    private Map<Long, String> listValue;
    private Map<Long, Timestamp> date;
    private Map<Long, String> references;

    public Pojo(long id, String name, long type_id) {
        this.id = id;
        this.name = name;
        this.type_id = type_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getType_id() {
        return type_id;
    }

    public void setType_id(long type_id) {
        this.type_id = type_id;
    }

    public Map<Long, String> getValues() {
        return values;
    }

    public void setValues(Map<Long, String> values) {
        this.values = values;
    }

    public Map<Long, Timestamp> getDate() {
        return date;
    }

    public void setDate(Map<Long, Timestamp> date) {
        this.date = date;
    }

    public Map<Long, String> getListValue() {
        return listValue;
    }

    public void setListValue(Map<Long, String> listValue) {
        this.listValue = listValue;
    }

    public Map<Long, String> getReferences() {
        return references;
    }

    public void setReferences(Map<Long, String> references) {
        this.references = references;
    }
}