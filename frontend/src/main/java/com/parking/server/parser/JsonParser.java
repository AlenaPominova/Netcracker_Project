package com.parking.server.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.List;

public abstract class JsonParser<T> {

    protected T setAttribute(T object, String attribute, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(attribute);
            field.setAccessible(true);

            if (field.getGenericType().toString().equals(Double.class.toString())) {
                Double d = new Double(String.valueOf(value));
                field.set(object, d);
            }
            if (field.getGenericType().toString().equals(String.class.toString())) {
                String d = new String(String.valueOf(value));
                field.set(object, d);
            }
            if (field.getGenericType().toString().equals(Long.class.toString())) {
                Long d = new Long(String.valueOf(value));
                field.set(object, d);
            }
            if (field.getGenericType().toString().equals(Integer.class.toString())) {
                Integer d = new Integer(String.valueOf(value));
                field.set(object, d);
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }
}
