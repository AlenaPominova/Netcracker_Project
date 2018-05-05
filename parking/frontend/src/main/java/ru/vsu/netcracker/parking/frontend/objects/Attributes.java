package ru.vsu.netcracker.parking.frontend.objects;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Attributes {

    private Map<Long, String> attributes = new HashMap<>();

    public Map<Long, String> getAll() {
        return attributes;
    }

    public void setAll(Map<Long, String> attributes) {
        this.attributes = attributes;
        //this.attributes.putAll(attributes);
    }

    public String get(long id) {
        String name = attributes.get(id);
        return attributes.get(id);
    }

    public void put(long id, String value) {
        attributes.put(id, value);
    }
}
