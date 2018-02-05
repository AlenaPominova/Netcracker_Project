package ru.vsu.netcracker.parking.backend.dao;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
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

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Generates a version 4 UUID
     * @return UUID string
     */
    public String getUUID() {
        return this.jdbcTemplate.queryForObject(PostgresSQLQueries.GET_UUID, String.class);
    }

    /** toDo
     * @param obj
     */
    @Override
    public String createObj(Obj obj) {
        String objectId = getUUID();
        jdbcTemplate.update(PostgresSQLQueries.INSERT_OBJECT_QUERY, new Object[]{objectId, obj.getTypeId(), obj.getName(), obj.getParentId(), obj.getDescription()});
        if (obj.getValues()!= null) {
            obj.getValues().forEach((attrId, value) -> {
                jdbcTemplate.update(PostgresSQLQueries.INSERT_PARAM_QUERY, new Object[]{attrId, obj.getId(), value});
            });
        }
        if (obj.getDateValues() != null) {
            obj.getValues().forEach((attrId, value) -> {
                jdbcTemplate.update(PostgresSQLQueries.INSERT_DATE_PARAM_QUERY, new Object[]{attrId, obj.getId(), value});
            });
        }
        if (obj.getListValues() != null) {
            obj.getValues().forEach((attrId, value) -> {
                jdbcTemplate.update(PostgresSQLQueries.INSERT_LIST_PARAM_QUERY, new Object[]{attrId, obj.getId(), attrId, value});
            });
        }
        if (obj.getReferences() != null) {
            obj.getValues().forEach((attrId, value) -> {
                jdbcTemplate.update(PostgresSQLQueries.INSERT_REFERENCES_QUERY, new Object[]{attrId, value, obj.getId()});
            });
        }
        return objectId;
    }

    /** toDo
     * @param obj
     */
    @Override
    public void updateObj(Obj obj) {
        if (obj.getValues() != null) {
            obj.getValues().forEach((attrId, value) -> {
                jdbcTemplate.update(PostgresSQLQueries.SET_PARAM_QUERY, new Object[]{attrId, obj.getId(), value, value});
            });
        }
        if (obj.getDateValues() != null) {
            obj.getValues().forEach((attrId, value) -> {
                jdbcTemplate.update(PostgresSQLQueries.SET_DATE_PARAM_QUERY, new Object[]{attrId, obj.getId(), value, value});
            });
        }
        if (obj.getListValues() != null) {
            obj.getValues().forEach((attrId, value) -> {
                jdbcTemplate.update(PostgresSQLQueries.SET_LIST_PARAM_QUERY, new Object[]{attrId, obj.getId(), attrId, value, attrId, value});
            });
        }
        if (obj.getReferences() != null) {
            obj.getValues().forEach((attrId, value) -> {
                jdbcTemplate.update(PostgresSQLQueries.SET_REFERENCE_PARAM_QUERY, new Object[]{attrId, obj.getId(), value, value});
            });
        }
    }

    /** toDo
     * @param objectId
     */
    @Override
    public void delete(String objectId) {
        long objectTypeId = jdbcTemplate.queryForObject(PostgresSQLQueries.GET_OBJECT_TYPE_ID_QUERY, new Object[]{objectId}, Long.class);
        List<Long> listOfParkingSpots = jdbcTemplate.queryForList(PostgresSQLQueries.GET_LIST_OF_PARKING_SPOTS_BY_CUSTOMER_ID_QUERY, new Object[]{objectId}, Long.class);
        if (objectTypeId == 2 && listOfParkingSpots.size() > 0) {  //если у польз-ля были в аренде парковки, их освободить
            for (Long spotId : listOfParkingSpots) {
                jdbcTemplate.update(PostgresSQLQueries.CHANGE_PARKING_SPOT_STATUS_QUERY, new Object[]{401, spotId});
            }
        }
        jdbcTemplate.update(PostgresSQLQueries.DELETE_OBJECT_QUERY, new Object[]{objectId});
    }

    /**
     * Reads basic information about object such as name, objectTypeId, parentId and description from the database by specified objectId
     * @param objectId
     * @return Obj populated with basic information such as name, objectTypeId, parentId and description
     */
    public Obj getBasicObjInfo(String objectId){
        return this.jdbcTemplate.queryForObject(PostgresSQLQueries.GET_OBJECT_INFO_BY_ID_QUERY, new Object[]{objectId}, (resultSet, i) -> {
            String name = resultSet.getString("name");
            long typeId = resultSet.getLong("object_type_id");
            String parentId = resultSet.getString("parent_id");
            String description = resultSet.getString("description");
            Obj object = new Obj(objectId, name, typeId, parentId);
            object.setDescription(description);
            return object;
        });
    }

    /** toDo
     * @param objectId
     * @return Obj
     */
    @Override
    public Obj getObj(String objectId) {
        Obj obj = getBasicObjInfo(objectId);
        Map<Long, String> values = new HashMap<>();;
        Map<Long, Timestamp> dateValues = new HashMap<>();;
        Map<Long, String> listValues = new HashMap<>();;
        Map<Long, Long> references = new HashMap<>();;

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
        jdbcTemplate.query(PostgresSQLQueries.GET_REFERENCE_VALUES_BY_OBJECT_ID_QUERY, new Object[]{objectId, objectId}, resultSet -> {
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

    /** toDo
     * @param objectId
     * @return JsonNode
     */
    @Override
    public JsonNode getObjAsJSON(String objectId) {
        Obj obj = getBasicObjInfo(objectId);
        Map<Long, String> values = new HashMap<>();
        Map<Long, Timestamp> dateValues = new HashMap<>();
        Map<Long, String> listValues = new HashMap<>();
        Map<Long, Long> references = new HashMap<>();
        Map<Long, String> refNames = new HashMap<>();
        Map<Long, String> attrNames = new HashMap<>();

        jdbcTemplate.query(PostgresSQLQueries.GET_VALUES_BY_OBJECT_ID_QUERY, new Object[]{objectId}, resultSet -> {
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
        });
        jdbcTemplate.query(PostgresSQLQueries.GET_REFERENCE_VALUES_BY_OBJECT_ID_QUERY, new Object[]{objectId, objectId}, resultSet -> {
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

        JsonConverter jsonConverter = new JsonConverter();
        return jsonConverter.writeJson(obj, attrNames, refNames);
    }
}
