package com.parking.server.objects;

import java.util.Map;

public class Singleton {

    private Map<Long, String> value;
    private static Singleton instance;

    private Singleton(Map<Long, String> values) {
        this.value = values;
    }

    public static Singleton getInstance(Map<Long, String> values) {
        if (instance == null) {
            instance = new Singleton(values);
        }
        return instance;
    }
}
