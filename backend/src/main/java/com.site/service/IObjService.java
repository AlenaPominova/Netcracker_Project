package com.site.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.site.objects.Pojo;

public interface IObjService {
    Pojo read(long id);
    JsonNode getJson(long id);
    JsonNode deleteById(long id);
    JsonNode create(Pojo obj);

}
