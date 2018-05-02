package com.parking.server.objects;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

public class Pojo implements Serializable {

    private long id;
    private String name;
    private long type_id;
    private Map<Long, String> values;
    private Map<Long, Long> listValue;
    private Map<Long, Timestamp> date;
    private Map<Long, Long> references;

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

    public Map<Long, Long> getListValue() {
        return listValue;
    }

    public void setListValue(Map<Long, Long> listValue) {
        this.listValue = listValue;
    }

    public Map<Long, Long> getReferences() {
        return references;
    }

    public void setReferences(Map<Long, Long> references) {
        this.references = references;
    }
}
