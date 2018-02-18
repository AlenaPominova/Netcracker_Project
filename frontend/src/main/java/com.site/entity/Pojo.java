package com.site.entity;

import java.sql.Timestamp;
import java.util.Map;

public class Pojo {

    private long id;
    private String name;
    private long type_id;
    private long parent_id;
    private Map<String, String> values;

    public Pojo(long id, String name, long type_id, long parent_id, Map<String, String> values){
        this.id = id;
        this.name = name;
        this.type_id = type_id;
        this.parent_id = parent_id;
        this.values = values;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getType_id() {
        return type_id;
    }

    public long getParent_id() {
        return parent_id;
    }

    public Map<String, String> getValues() {
        return values;
    }
}
