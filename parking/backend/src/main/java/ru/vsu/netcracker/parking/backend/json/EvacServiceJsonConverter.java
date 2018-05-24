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
    private final long ADDRESS_ATTRIBUTE_ID = 101L;
    private final long LATITUDE_ATTRIBUTE_ID = 301L;
    private final long LONGITUDE_ATTRIBUTE_ID = 302L;

    public static final long EVAC_TO_PARKING_ID = 12L;
    public static final String EVAC_TO_LATITUDE = "51.700701";
    public static final String EVAC_TO_LONGITUDE = "39.130788";

    public JsonNode createJsonRequest(Obj user, Obj parking) {
        JsonNodeFactory factory = new JsonNodeFactory(false);
        ObjectNode json = factory.objectNode();

        String[] names = user.getName().split(" ");
        if (names.length == 2) {
            json.put("clientFirstName", names[0]);
            json.put("clientLastName", names[1]);
        } else {
            json.put("clientFirstName", user.getName());
            json.put("clientLastName", "MockLastName");
        }

        json.put("clientPhoneNumber", user.getValues().get(PHONE_ATTRIBUTE_ID));
        json.put("address", parking.getValues().get(ADDRESS_ATTRIBUTE_ID));
        json.put("geoData", String.join(",", parking.getValues().get(LATITUDE_ATTRIBUTE_ID), parking.getValues().get(LONGITUDE_ATTRIBUTE_ID)));
        json.put("destinationGeoData", String.join(",", EVAC_TO_LATITUDE, EVAC_TO_LONGITUDE));
        return json;
    }

    public long parseJsonResponse(JsonNode json) {
        return json.path("id").asLong();
    }
}
