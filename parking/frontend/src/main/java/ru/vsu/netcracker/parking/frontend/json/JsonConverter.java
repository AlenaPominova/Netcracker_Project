package ru.vsu.netcracker.parking.frontend.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.vsu.netcracker.parking.frontend.objects.Attributes;
import ru.vsu.netcracker.parking.frontend.objects.Obj;
import ru.vsu.netcracker.parking.frontend.services.ObjService;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Component
public class JsonConverter {

    private Attributes attributes;
    private ObjService objService;

    @Autowired
    ApplicationContext context;

    @Autowired
    public JsonConverter(Attributes attributes) {
        this.attributes = attributes;
    }

    public JsonNode objToJson(Obj obj) {

        this.objService = (ObjService) context.getBean("objService");

        JsonNodeFactory factory = new JsonNodeFactory(false);
        ObjectNode json = factory.objectNode();

        json.put("id", obj.getId());
        json.put("name", obj.getName());
        json.put("type_id", obj.getTypeId());
        json.put("description", obj.getDescription());

        ArrayNode values = factory.arrayNode();
        if (!obj.getValues().isEmpty()) {
            obj.getValues().forEach((k, v) -> {
                ObjectNode value = factory.objectNode();
                value.put("id", k);
                value.put("name", attributes.get(k));
                value.put("value", v);
                values.add(value);
            });
            json.put("values", values);
        }

        ArrayNode dateValues = factory.arrayNode();
        if (!obj.getDateValues().isEmpty()) {
            obj.getDateValues().forEach((k, v) -> {
                ObjectNode value = factory.objectNode();
                value.put("id", k);
                value.put("name", attributes.get(k));
                value.put("value", v.toString());
                dateValues.add(value);
            });
            json.put("dateValues", dateValues);
        }

        ArrayNode listValues = factory.arrayNode();
        if (!obj.getListValues().isEmpty()) {
            obj.getListValues().forEach((k, v) -> {
                ObjectNode value = factory.objectNode();
                value.put("id", k);
                value.put("name", attributes.get(k));
                value.put("value", v);
                listValues.add(value);
            });
            json.put("listValues", listValues);
        }

        ArrayNode references = factory.arrayNode();
        if (!obj.getReferences().isEmpty()) {
            obj.getReferences().forEach((k, v) -> {
                ObjectNode value = factory.objectNode();
                value.put("id", k);
                value.put("name", attributes.get(k));
                value.put("reference", v);
//                value.put("referenceName",objService.get(v).getName());
                references.add(value);
            });
            json.put("references", references);
        }

        ArrayNode links = addLinksToJson(obj);
        json.put("links", links);

        return json;
    }

    private ArrayNode addLinksToJson(Obj obj) {
        JsonNodeFactory factory = new JsonNodeFactory(false);
        ArrayNode links = factory.arrayNode();
        ObjectNode link = factory.objectNode();

        if (obj.getTypeId() == 2) {
            link.put("rel", "self");
            link.put("href", "http://localhost:8080/backend/profiles/" + obj.getId());
            links.add(link);
        } else if (obj.getTypeId() == 3) {
            link = factory.objectNode();
            link.put("rel", "self");
            link.put("href", "http://localhost:8080/backend/parkings/" + obj.getId());
            links.add(link);
            link = factory.objectNode();
            link.put("rel", "owner");
            link.put("href", "http://localhost:8080/backend/profiles/" + obj.getReferences().get(Long.valueOf(300)));
            links.add(link);
        }
        return links;
    }

    public Obj jsonToObject(JsonNode json) {
        long id = json.path("id").asLong();
        String name = json.path("name").asText();
        long typeId = json.path("type_id").asLong();
        String description = json.path("description").asText();

        Map<Long, String> values = new HashMap<>();
        Map<Long, Timestamp> dateValues = new HashMap<>();
        Map<Long, String> listValues = new HashMap<>();
        Map<Long, Long> references = new HashMap<>();

        JsonNode valuesNode = json.path("values");
        for (JsonNode value : valuesNode) {
            values.put(value.path("id").asLong(), value.path("value").asText());
        }

        JsonNode dateValuesNode = json.path("dateValues");
        for (JsonNode value : dateValuesNode) {
            String date = value.path("value").asText();
            dateValues.put(value.path("id").asLong(), Timestamp.valueOf(date));
        }

        JsonNode listValuesNode = json.path("listValues");
        for (JsonNode value : listValuesNode) {
            listValues.put(value.path("id").asLong(), value.path("value").asText());
        }

        JsonNode referencesNode = json.path("references");
        for (JsonNode value : referencesNode) {
            references.put(value.path("id").asLong(), value.path("reference").asLong());
        }

        Obj obj = new Obj(id, name, typeId);
        obj.setDescription(description);
        obj.setValues(values);
        obj.setDateValues(dateValues);
        obj.setListValues(listValues);
        obj.setReferences(references);

        return obj;
    }
}
