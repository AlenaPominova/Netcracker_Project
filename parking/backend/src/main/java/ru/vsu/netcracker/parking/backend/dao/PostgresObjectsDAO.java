package ru.vsu.netcracker.parking.backend.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vsu.netcracker.parking.backend.json.JsonConverter;
import ru.vsu.netcracker.parking.backend.models.Obj;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostgresObjectsDAO implements ObjectsDAO {

    private JdbcTemplate jdbcTemplate;
    private JsonConverter jsonConverter;

    @Autowired
    public PostgresObjectsDAO(DataSource dataSource, JsonConverter jsonConverter) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jsonConverter = jsonConverter;
    }

    /**
     * Get new GUID from database function in format "YYMMDDSSMS + random'
     *
     * @return GUID
     */
    private Long getGUID() {
        return this.jdbcTemplate.queryForObject(PostgresSQLQueries.GET_GUID, Long.class);
    }

    /**
     * Get next value from database sequence for Parking objects
     *
     * @return new Parking Id
     */
    private Long getParkingId() {
        return this.jdbcTemplate.queryForObject(PostgresSQLQueries.GET_PARKING_ID, Long.class);
    }

    /**
     * Inserts values, On conflict updates values
     *
     * @param obj
     */
    private void insertOrUpdateValues(Obj obj) {
        if (obj.getValues() != null) {
            obj.getValues().forEach((attrId, value) -> {
                this.jdbcTemplate.update(PostgresSQLQueries.SET_PARAM_QUERY, new Object[]{attrId, obj.getId(), value, value});
            });
        }
        if (obj.getDateValues() != null) {
            obj.getDateValues().forEach((attrId, value) -> {
                this.jdbcTemplate.update(PostgresSQLQueries.SET_DATE_PARAM_QUERY, new Object[]{attrId, obj.getId(), value, value});
            });
        }
        if (obj.getListValues() != null) {
            obj.getListValues().forEach((attrId, value) -> {
                this.jdbcTemplate.update(PostgresSQLQueries.SET_LIST_PARAM_QUERY, new Object[]{attrId, obj.getId(), attrId, value, attrId, value});
            });
        }
        if (obj.getReferences() != null) {
            obj.getReferences().forEach((attrId, value) -> {
                this.jdbcTemplate.update(PostgresSQLQueries.SET_REFERENCE_PARAM_QUERY, new Object[]{attrId, value, obj.getId(), value});
            });
        }
    }

    /**
     * Inserts object. If object with such id already exists - updates its values
     * If there is no id assigned to the object - sets new GUID
     * If there is no name assigned to the object of type "Parking" - sets unique id from sequence
     *
     * @param obj
     */
    @Override
    public void saveObj(Obj obj) {
        if (obj.getId() == 0) {
            obj.setId(getGUID());
        }
        if (obj.getName() == null && obj.getTypeId() == 3) {
            obj.setName(getParkingId().toString());
        }
        this.jdbcTemplate.update(PostgresSQLQueries.SAVE_OBJECT_QUERY, new Object[]{obj.getId(), obj.getTypeId(), obj.getName(), obj.getDescription(), obj.getName()});
        insertOrUpdateValues(obj);
    }

    /**
     * Removes the object with the specified id
     *
     * @param objectId - id of the object to be removed
     */
    @Override
    public void delete(long objectId) {
        this.jdbcTemplate.update(PostgresSQLQueries.DELETE_OBJECT_QUERY, new Object[]{objectId});
    }

    /**
     * Reads basic information about object such as name, objectTypeId and description from the database by specified objectId
     *
     * @param objectId - id of object
     * @return Obj populated with basic information such as name, objectTypeId, parentId and description
     */
    private Obj getBasicObjInfo(long objectId) {
        return this.jdbcTemplate.queryForObject(PostgresSQLQueries.GET_OBJECT_INFO_BY_ID_QUERY, new Object[]{objectId}, (resultSet, i) -> {
            String name = resultSet.getString("name");
            long typeId = resultSet.getLong("object_type_id");
            String description = resultSet.getString("description");
            Obj object = new Obj(objectId, name, typeId);
            object.setDescription(description);
            return object;
        });
    }

    /**
     * Returns the object with the specified id
     *
     * @param objectId - id of the object to return
     * @return Obj with the specified id
     */
    @Override
    public Obj getObj(long objectId) {
        Obj obj = getBasicObjInfo(objectId);
        Map<Long, String> values = new HashMap<>();
        Map<Long, Timestamp> dateValues = new HashMap<>();
        Map<Long, String> listValues = new HashMap<>();
        Map<Long, Long> references = new HashMap<>();

        this.jdbcTemplate.query(PostgresSQLQueries.GET_VALUES_BY_OBJECT_ID_QUERY, new Object[]{objectId}, resultSet -> {
            while (resultSet.next()) {
                if (resultSet.getString("value") != null) {
                    values.put(resultSet.getLong("attr_id"), resultSet.getString("value"));
                } else if (resultSet.getString("date_value") != null) {
                    dateValues.put(resultSet.getLong("attr_id"), resultSet.getTimestamp("date_value"));
                } else {
                    listValues.put(resultSet.getLong("attr_id"), resultSet.getString("list_value"));
                }
            }
            return null;
        });
        this.jdbcTemplate.query(PostgresSQLQueries.GET_REFERENCE_VALUES_BY_OBJECT_ID_QUERY, new Object[]{objectId, objectId}, resultSet -> {
            while (resultSet.next()) {
                references.put(resultSet.getLong("attr_id"), resultSet.getLong("reference"));
            }
            return null;
        });
        obj.setValues(values);
        obj.setDateValues(dateValues);
        obj.setListValues(listValues);
        obj.setReferences(references);
        return obj;
    }

    /**
     * Returns JSON representation of the object with the specified id
     *
     * @param objectId - id of the object to return
     * @return JsonNode
     */
    @Override
    public JsonNode getObjAsJSON(long objectId) {
        Obj obj = getBasicObjInfo(objectId);
        Map<Long, String> values = new HashMap<>();
        Map<Long, Timestamp> dateValues = new HashMap<>();
        Map<Long, String> listValues = new HashMap<>();
        Map<Long, Long> references = new HashMap<>();
        Map<Long, String> refNames = new HashMap<>();
        Map<Long, String> attrNames = new HashMap<>();

        this.jdbcTemplate.query(PostgresSQLQueries.GET_VALUES_BY_OBJECT_ID_QUERY, new Object[]{objectId}, resultSet -> {
            while (resultSet.next()) {
                if (resultSet.getString("value") != null) {
                    values.put(resultSet.getLong("attr_id"), resultSet.getString("value"));
                } else if (resultSet.getString("date_value") != null) {
                    dateValues.put(resultSet.getLong("attr_id"), resultSet.getTimestamp("date_value"));
                } else {
                    listValues.put(resultSet.getLong("attr_id"), resultSet.getString("list_value"));
                }
                attrNames.put(resultSet.getLong("attr_id"), resultSet.getString("name"));
            }
            return null;
        });

        this.jdbcTemplate.query(PostgresSQLQueries.GET_REFERENCE_VALUES_BY_OBJECT_ID_QUERY, new Object[]{objectId, objectId}, resultSet -> {
            while (resultSet.next()) {
                references.put(resultSet.getLong("attr_id"), resultSet.getLong("reference"));
                refNames.put(resultSet.getLong("attr_id"), resultSet.getString("referenced_object_name"));
                attrNames.put(resultSet.getLong("attr_id"), resultSet.getString("name"));
            }
            return null;
        });
        obj.setValues(values);
        obj.setDateValues(dateValues);
        obj.setListValues(listValues);
        obj.setReferences(references);

        return jsonConverter.objToJson(obj, refNames);
    }

    /**
     * Returns Json Array of objects of a certain type
     *
     * @param objectType
     * @return Array of objects of a certain type
     */
    @Override
    public ArrayNode getAllObjAsJSON(String objectType) {
        JsonNodeFactory factory = new JsonNodeFactory(false);
        ArrayNode jsonObjectsList = factory.arrayNode();
        List<Long> objectsList = this.jdbcTemplate.queryForList(PostgresSQLQueries.GET_LIST_OF_OBJECTS_BY_OBJECT_TYPE, new Object[]{objectType}, Long.class);
        for (Long objectId : objectsList) {
            JsonNode node = getObjAsJSON(objectId);
            jsonObjectsList.add(node);
        }
        return jsonObjectsList;
    }

    /**
     * Returns object with given username
     *
     * @param userName - either email or phone
     * @return object with given username
     */
    @Override
    public Obj getObjByUserName(String userName) {
        long objectId = this.jdbcTemplate.queryForObject(PostgresSQLQueries.GET_OBJECT_ID_BY_LOGIN, new Object[]{201L, userName, 202L, userName}, Long.class);
        return getObj(objectId);
    }

    /**
     * Returns Map with all attributes, where key is Attribute Id and Value is Attribute Name
     *
     * @return
     */
    @Override
    public Map<Long, String> getAttributes() {
        Map<Long, String> attributes = new HashMap<>();
        this.jdbcTemplate.query(PostgresSQLQueries.GET_ATTRIBUTES_INFO, resultSet -> {
            while (resultSet.next()) {
                attributes.put(resultSet.getLong("attr_id"), resultSet.getString("name"));
            }
            return null;
        });
        return attributes;
    }
}
