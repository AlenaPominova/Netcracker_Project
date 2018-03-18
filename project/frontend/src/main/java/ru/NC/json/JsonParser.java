package ru.NC.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

public abstract class JsonParser<T> {

    public abstract T parseJson(JsonNode rootNode);

    public abstract List<T> parseAll(ArrayNode dataSet);

    protected T setAttribute(T object, String attribute, Object value){
        try {
            Field field = object.getClass().getDeclaredField(attribute);
            field.setAccessible(true);
            if (field.getClass().getSimpleName().equals(Double.class.getSimpleName())){
                field.set(object, (Double) value);
            } else {
                field.set(object, value);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }
}
