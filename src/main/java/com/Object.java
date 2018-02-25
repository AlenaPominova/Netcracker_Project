package com;

public class Object {

    private int object_id;
    private int object_type_id;
    private String name;
    private int parent_id;


    public int getId() {

        return object_id;
    }

    public void setId(int id) {

        this.object_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getObject_type_id() {
        return object_type_id;
    }

    public void setObject_type_id(int object_type_id) {
        this.object_type_id = object_type_id;
    }
}