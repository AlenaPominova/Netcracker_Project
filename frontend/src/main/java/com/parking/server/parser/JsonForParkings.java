package com.parking.server.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.parking.server.objects.Parking;

import java.util.*;

public class JsonForParkings extends JsonParser<Parking>  {

    public List<Parking> parseAll(ArrayNode dataSet){
        List<Parking> parkings = new ArrayList<Parking>();
        Iterator datasetElements = dataSet.iterator();
        while (datasetElements.hasNext()) {
            JsonNode jsonParking = (JsonNode) datasetElements.next();
            Parking parking = parseJson(jsonParking);
            parkings.add(parking);
        }
        return parkings;
    }

    public Parking parseJson(JsonNode rootNode) {

        Parking parking = new Parking(rootNode.get("id").asLong());
        ArrayNode values = (ArrayNode) rootNode.get("values");
        ArrayNode dates = (ArrayNode) rootNode.get("date");
        Map<Long, String> map_attr = new HashMap<>();

        if (values != null) {
            for (JsonNode cur : values) {
                map_attr.put(cur.get("id").asLong(), cur.get("name").asText());

                parking = setAttribute(parking, cur.get("name").asText(), cur.get("value").asText());
            }
        }
        if (dates != null) {
            for (JsonNode cur : dates) {
                map_attr.put(cur.get("id").asLong(), cur.get("name").asText());
                parking = setAttribute(parking, cur.get("name").asText(), cur.get("value").asText());
            }
        }
        parking.setAttr_name(map_attr);

        return parking;
    }
}
