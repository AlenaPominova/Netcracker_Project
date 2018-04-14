package com.site.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.site.objects.Pojo;

public interface IObjDAO {
    JsonNode create(Pojo obj);
    Pojo read(long id);
    JsonNode update(Pojo obj);
    JsonNode delete(long id);
    JsonNode getJson(long id);
    JsonNode getByObjectType(String objectType);
}
