package ru.NC.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.LocalDate;
import ru.NC.models.Obj;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JsonCreating {

    public static Obj parseJson(ObjectNode rootNode){

        Long id = rootNode.path("Id").asLong();
        Long typeId = rootNode.path("type_id").asLong();
        Long parentId = rootNode.path("parent_id").asLong();
        String name = rootNode.path("name").asText();

        Obj object = new Obj(typeId, parentId, name);
        object.setId(id);

        Map<Long, String> values = new HashMap<>();
        JsonNode valNode = rootNode.path("values");

        if (!valNode.isMissingNode()) {
            for (JsonNode node : valNode) {
                values.put(node.path("id").asLong(), node.path("value").asText());
            }
            object.setValues(values);
        }

        Map<Long, Timestamp> date = new HashMap<>();
        JsonNode dateNode = rootNode.path("date_values");

        if (!dateNode.isMissingNode()) {
            for (JsonNode node : dateNode) {
                date.put(node.path("id").asLong(), Timestamp.valueOf(node.path("value").asText()));
            }
            object.setDate(date);
        }

        Map<Long, Long> ref = new HashMap<>();
        JsonNode refNode = rootNode.path("references");

        if (refNode.isMissingNode()) {
            // if "name" node is missing
        } else {
            for (JsonNode node : refNode) {
                ref.put(node.path("attr_id").asLong(), node.path("ref_id").asLong());
            }
            object.setReference(ref);
        }

        return object;
    }

    public static ObjectNode createJson(Obj obj) {

        JsonNodeFactory factory = new JsonNodeFactory(false);

        // the root node
        ObjectNode jsonObject = factory.objectNode();

        jsonObject.put("Id", obj.getId());
        jsonObject.put("Name", obj.getName());
        jsonObject.put("Description", obj.getDescription());
        jsonObject.put("Type id", obj.getTypeId());
        jsonObject.put("Parent id", obj.getParentId());

        ObjectNode values = factory.objectNode();
        try{
            for (Map.Entry entry: obj.getValues().entrySet()) {
                values.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }catch (NullPointerException e){
            //TODO: logging
            System.out.println("values are null");
        }
        jsonObject.put("values", values);

        ObjectNode date = factory.objectNode();
        try {
            for (Map.Entry entry : obj.getDate().entrySet()) {
                date.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }catch (NullPointerException e){
            //TODO: logging
            System.out.println("date is null");
        }
        jsonObject.put("date", date);


        ObjectNode ref = factory.objectNode();
        try {
            for (Map.Entry entry : obj.getReference().entrySet()) {
                ref.put(entry.getKey().toString(), entry.getValue().toString());
            }
        } catch (NullPointerException e){
            //TODO: logging
            System.out.println("refs are null");
        }
        jsonObject.put("references", ref);

        return jsonObject;
    }
}
