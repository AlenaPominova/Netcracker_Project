package ru.NC.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import ru.NC.models.Parking;
import ru.NC.models.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParkingParser extends JsonParser<Parking> {
    public Parking parseJson(JsonNode parkingJson) {

        Long id = parkingJson.path("Id").asLong();
        JsonNode values = parkingJson.get("values");
        JsonNode references = parkingJson.get("references");
        Iterator valIterator;
        Parking parking = new Parking(id);
        if (!values.isMissingNode()){
            valIterator = values.iterator();
            while (valIterator.hasNext()){
                JsonNode value = (JsonNode) valIterator.next();
                parking = setAttribute(parking, value.get("name").asText(), value.get("value").asText());
            }
        }
        Iterator refIterator;
        if (!references.isMissingNode()) {
            refIterator = references.iterator();
            while (refIterator.hasNext()){
                JsonNode ref = (JsonNode) refIterator.next();
                User user = new User(ref.get("ref_id").asLong());
                //достать инфу об этом юзере, так как он уже должен существовать в базе
//            parking = setAttribute(parking, "owner", user);
            }
        }
        return parking;
    }

    public List<Parking> parseAll(ArrayNode dataSet) {
        List<Parking> parkings = new ArrayList<Parking>();
        Iterator datasetElements = dataSet.iterator();
        while (datasetElements.hasNext()) {
            JsonNode jsonParking = (JsonNode) datasetElements.next();
            Parking parking = parseJson(jsonParking);
            parkings.add(parking);
        }
        return parkings;
    }
}
