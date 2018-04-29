package ru.vsu.netcracker.parking.frontend.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import ru.vsu.netcracker.parking.frontend.objects.Obj;

@Component
public class EvacServiceJsonConverter {

    private final long PHONE_ATTRIBUTE_ID = 201L;
    private final long ADDRESS_ATTRIBUTE_ID = 303L;
    private final long LATITUDE_ATTRIBUTE_ID = 301L;
    private final long LONGITUDE_ATTRIBUTE_ID = 302L;

    public JsonNode createJsonRequest(Obj obj) {
        JsonNodeFactory factory = new JsonNodeFactory(false);
        ObjectNode json = factory.objectNode();

        json.put("clientFirstName", obj.getName());
        json.put("clientPhoneNumber", obj.getValues().get(PHONE_ATTRIBUTE_ID));
        String str = obj.getValues().get(ADDRESS_ATTRIBUTE_ID);
        json.put("address", obj.getValues().get(ADDRESS_ATTRIBUTE_ID));
        json.put("geoData", String.join(",", obj.getValues().get(LATITUDE_ATTRIBUTE_ID), obj.getValues().get(LONGITUDE_ATTRIBUTE_ID)));

        return json;
    }

    public long parseJsonResponse(JsonNode json) {
        //String orderStatus = json.path("statusOrder").asText();
        String orderName = json.path("name").asText();
        long evacOrderId = Long.valueOf(orderName.substring(6));
        return evacOrderId;
    }
}
