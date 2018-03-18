package ru.NC.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import ru.NC.models.User;
import ru.NC.models.UserRole;

import java.lang.reflect.Field;
import java.util.*;

public class UserParser extends JsonParser<User> {

    public User parseJson(JsonNode jsonUser){
        Long id = jsonUser.path("Id").asLong();
        JsonNode values = jsonUser.get("values");
        JsonNode references = jsonUser.get("references");
        Iterator valIterator = values.iterator();
        Iterator refIterator = references.iterator();
        User user = new User(id);

        while (valIterator.hasNext()){
            JsonNode value = (JsonNode) valIterator.next();
            user = setAttribute(user, value.get("name").asText(), value.get("value").asText());
        }

        while (refIterator.hasNext()){
            JsonNode ref = (JsonNode) refIterator.next();
            UserRole role = new UserRole(ref.get("ref_id").asLong());
            role.setName(ref.get("value").asText());
//            user = setAttribute(user, "userRole", role);
        }

        return user;
    }

    public List<User> parseAll(ArrayNode dataSet){
        List<User> users = new ArrayList<User>();
        Iterator datasetElements = dataSet.iterator();
        while (datasetElements.hasNext()) {
            JsonNode jsonUser = (JsonNode) datasetElements.next();
            User user = parseJson(jsonUser);
            users.add(user);
        }
        return users;
    }
}
