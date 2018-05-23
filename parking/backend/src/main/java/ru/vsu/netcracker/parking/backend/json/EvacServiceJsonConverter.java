package ru.vsu.netcracker.parking.backend.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import ru.vsu.netcracker.parking.backend.models.Obj;

@Component
public class EvacServiceJsonConverter {

    private final long PHONE_ATTRIBUTE_ID = 201L;
    private final long ADDRESS_ATTRIBUTE_ID = 303L;
    private final long LATITUDE_ATTRIBUTE_ID = 301L;
    private final long LONGITUDE_ATTRIBUTE_ID = 302L;

    //ToDo CHANGE THESE VALUES  (on frontend aswell)
    public static final long EVAC_TO_PARKING_ID = 10L;
    public static final String EVAC_TO_LATITUDE = "51.666499";
    public static final String EVAC_TO_LONGITUDE = "39.191790";

    public JsonNode createJsonRequest(Obj obj) {
        JsonNodeFactory factory = new JsonNodeFactory(false);
        ObjectNode json = factory.objectNode();

        String[] names = obj.getName().split(" ");
        if(names.length == 2) {
            json.put("clientFirstName", names[0]);
            json.put("clientLastName", names[1]);
        }

        json.put("clientFirstName", obj.getName());
        json.put("clientLastName", "");
        json.put("clientPhoneNumber", obj.getValues().get(PHONE_ATTRIBUTE_ID));
        json.put("address", obj.getValues().get(ADDRESS_ATTRIBUTE_ID));
        json.put("geoData", String.join(",", obj.getValues().get(LATITUDE_ATTRIBUTE_ID), obj.getValues().get(LONGITUDE_ATTRIBUTE_ID)));
        json.put("destinationGeoData", String.join(",", EVAC_TO_LATITUDE, EVAC_TO_LONGITUDE));

        return json;
    }

    public long parseJsonResponse(JsonNode json) {
        return json.path("id").asLong();
    }
}
