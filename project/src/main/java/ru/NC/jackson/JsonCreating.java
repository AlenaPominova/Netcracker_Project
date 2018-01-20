package ru.NC.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.NC.models.Obj;

import java.util.Map;

public class JsonCreating {

    public static JsonNode createJson(Obj obj) {

        JsonNodeFactory factory = new JsonNodeFactory(false);

        // the root node - album
        JsonNode object = factory.objectNode();

        ((ObjectNode) object).put("Id", obj.getId());
        ((ObjectNode) object).put("Name", obj.getName());
        ((ObjectNode) object).put("Description", obj.getDescription());
        ((ObjectNode) object).put("Type id", obj.getTypeId());
        ((ObjectNode) object).put("Parent id", obj.getParentId());

        ObjectNode values = factory.objectNode();
        try{
            for (Map.Entry entry: obj.getValues().entrySet()) {
                values.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }catch (NullPointerException e){
            //TODO: logging
            System.out.println("values are null");
        }
        ((ObjectNode) object).put("values", values);

        ObjectNode date = factory.objectNode();
        try {
            for (Map.Entry entry : obj.getDate().entrySet()) {
                date.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }catch (NullPointerException e){
            //TODO: logging
            System.out.println("date is null");
        }
        ((ObjectNode) object).put("date", date);


        ObjectNode ref = factory.objectNode();
        try {
            for (Map.Entry entry : obj.getReference().entrySet()) {
                ref.put(entry.getKey().toString(), entry.getValue().toString());
            }
        } catch (NullPointerException e){
            //TODO: logging
            System.out.println("refs are null");
        }
        ((ObjectNode) object).put("references", ref);

        return object;
    }
}
