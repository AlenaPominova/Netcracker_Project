package ru.NC.dao;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.NC.models.Obj;
import ru.NC.models.Queries;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


/**
 * DAO layer class of {@link Obj}
 * @author Alena Pominova
 * @version 1.0
 */
public class ObjectDaoImpl implements IObjectDao{

    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    /**
     * method for reading all information of object by id
     * @param id
     * @return obj
     * @see ObjectDaoImpl#getValues(long, long)
     * @see ObjectDaoImpl#getDate(long, long)
     * @see ObjectDaoImpl#getReference(long, long)
     * @see Queries
     */
    public Obj read(final int id) {
        try {
            Obj object = jdbcTemplateObject.queryForObject(Queries.GET_OBJECT_INFO,
                    (resultSet, i) -> {

                        if (resultSet == null) {
                            return new Obj();
                        }
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        Long typeId = resultSet.getLong("type_id");

                        Obj obj = new Obj();
                        obj.setId(id);
                        obj.setName(name);
                        obj.setDescription(description);
                        obj.setTypeId(typeId);
                        return obj;
                    }, id);

            object.setValues(getValues(object.getTypeId(), id));
            object.setDate(getDate(object.getTypeId(), id));
            object.setReference(getReference(object.getTypeId(), id));
            //object.setListValue(null);
            return object;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    /**
     * method for creating new object
     * @param object
     * @see Queries
     */
    public void create(Obj object) {
        Object[] params = new Object[]{object.getName(), object.getDescription(), object.getTypeId()};
        jdbcTemplateObject.update(Queries.CREATE_OBJECT, params);

        if (object.getValues() != null) {
            for (Map.Entry entry : object.getValues().entrySet()) {
                jdbcTemplateObject.update(Queries.INSERT_PARAMS, object.getId(), entry.getKey(), entry.getValue(), null, null);
            }
        }

        if (object.getDate() != null) {
            for (Map.Entry entry : object.getDate().entrySet()) {
                jdbcTemplateObject.update(Queries.INSERT_PARAMS, object.getId(), entry.getKey(), null,
                        entry.getValue(), null, null);
            }
        }

        if (object.getReference() != null) {
            for (Map.Entry entry : object.getReference().entrySet()) {
                jdbcTemplateObject.update(Queries.INSERT_REFERENCES, object.getId(), entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * method for updating object
     * @param object
     * @return obj
     * @see Queries
     */
    public void update(Obj object) {
        if (object.getValues() != null) {
            for (Map.Entry entry : object.getValues().entrySet()) {
                jdbcTemplateObject.update(Queries.SET_PARAMETER, entry.getKey(), object.getId(), entry.getValue());
            }
        }

        if (object.getDate() != null) {
            for (Map.Entry entry : object.getDate().entrySet()) {
                jdbcTemplateObject.update(Queries.SET_DATE, entry.getKey(), object.getId(), entry.getValue());
            }
        }

        if (object.getReference() != null) {
            for (Map.Entry entry : object.getReference().entrySet()) {
                jdbcTemplateObject.update(Queries.SET_REFERENCE, entry.getKey(), object.getId(), entry.getValue());
            }
        }
    }

    /**
     * method for deleting all information of object by id
     * @param id
     * @see Queries
     */
    public void delete(int id) {
        int result = jdbcTemplateObject.update(Queries.DELETE, id);
    }

    /**
     * get only values (Map<id, value>) from Params table
     * @param id
     * @return Map<Long, String>
     * @see Queries
     */
    private Map<Long, String> getValues(long typeId, long id) {
        return jdbcTemplateObject.query(Queries.GET_VALUES, (rs) -> {
            Map<Long, String> map = new HashMap<>();
            while(rs.next()){
                map.put(rs.getLong("name"), rs.getString("value"));
            }
            return map;
        }, typeId, id);
    }

    /**
     * get only date_values (Map<id, value>) from Params table
     * @param id
     * @return Map<Long, Timestamp>
     * @see Queries
     */
    private Map<Long, Timestamp> getDate(long typeId, long id) {

        return jdbcTemplateObject.query(Queries.GET_DATE, (rs) -> {
            Map<Long, Timestamp> map = new HashMap<>();
            while(rs.next()){
                map.put(rs.getLong("name"), Timestamp.valueOf(rs.getString("value")));
            }
            return map;
        }, typeId, id);
    }

    /**
     * get only references (Map<id, value>) from References table
     * @param id
     * @return Map<Long, String>
     * @see Queries
     */
    private Map<Long, Long> getReference(long typeId, long id) {
        return jdbcTemplateObject.query(Queries.GET_REFERENCE, (rs) -> {
            Map<Long, Long> map = new HashMap<>();
            while(rs.next()){
                map.put(rs.getLong("name"), rs.getLong("value"));
            }
            return map;
        }, typeId, id);
    }

    public void setParameter(int objId, int attributeId, String value) {
        jdbcTemplateObject.update(Queries.SET_PARAMETER, attributeId, objId, value);
    }

    public void setReference(int objId, int attributeId, Map<Long, String> value) {
        jdbcTemplateObject.update(Queries.SET_REFERENCE, attributeId, objId, value);
    }

    public void setDate(int objId, int attributeId, Map<Long, String> value) {
        jdbcTemplateObject.update(Queries.SET_DATE, attributeId, objId, value);
    }

    /**
     * method creates json by object`s id
     * @param id
     * @return JsonNode
     * @see Queries
     */
    public ObjectNode getObjectAsJson(long id) {
        JsonNodeFactory factory = new JsonNodeFactory(false);

        ObjectNode rootNode = factory.objectNode();;
        try {
            rootNode = jdbcTemplateObject.queryForObject(Queries.GET_OBJECT_INFO, new RowMapper<ObjectNode>() {
                @Nullable
                @Override
                public ObjectNode mapRow(ResultSet rs, int i) throws SQLException {
                    ObjectNode jsonObj = factory.objectNode();
                    jsonObj.put("name", rs.getString("name"));
                    jsonObj.put("type_id", rs.getLong("type_id"));

                    return jsonObj;
                }
            }, id);
            rootNode.put("Id", id);
        } catch (EmptyResultDataAccessException e){
            e.printStackTrace();
        }

        ArrayNode values = factory.arrayNode();
        try {
            values = jdbcTemplateObject.queryForObject(Queries.GET_VALUES_JSON, new RowMapper<ArrayNode>() {
                @Nullable
                @Override
                public ArrayNode mapRow(ResultSet rs, int i) throws SQLException {
                    JsonNodeFactory factory = new JsonNodeFactory(false);
                    ArrayNode jsonObj1 = factory.arrayNode();

                    while (rs.next()) {
                        ObjectNode jsonObj = factory.objectNode();
                        jsonObj.put("id", rs.getLong("id"));
                        jsonObj.put("name", rs.getString("name"));
                        jsonObj.put("value", rs.getString("value"));
                        jsonObj1.add(jsonObj);
                    }
                    return jsonObj1;
                }
            }, id);
            rootNode.put("values", values);
        } catch (EmptyResultDataAccessException e){
           e.printStackTrace();
        }

        ArrayNode date = factory.arrayNode();
        try {
            date = jdbcTemplateObject.queryForObject(Queries.GET_DATE_JSON, new RowMapper<ArrayNode>() {
                @Nullable
                @Override
                public ArrayNode mapRow(ResultSet rs, int i) throws SQLException {
                    JsonNodeFactory factory = new JsonNodeFactory(false);
                    ArrayNode jsonObj1 = factory.arrayNode();
                    if (rs != null) {
                        do {
                            ObjectNode jsonObj = factory.objectNode();
                            jsonObj.put("id", rs.getLong("id"));
                            jsonObj.put("name", rs.getString("name"));
                            jsonObj.put("value", rs.getString("value"));
                            jsonObj1.add(jsonObj);
                        }while (rs.next());
                    }
                    return jsonObj1;
                }
            }, id);
            rootNode.put("date_values", date);
        } catch (EmptyResultDataAccessException e){
           e.printStackTrace();
        }

        ArrayNode ref = factory.arrayNode();
        try {
            ref = jdbcTemplateObject.queryForObject(Queries.GET_REFERENCE_JSON, new RowMapper<ArrayNode>() {
                @Nullable
                @Override
                public ArrayNode mapRow(ResultSet rs, int i) throws SQLException {
                    ArrayNode jsonObj1 = factory.arrayNode();
                    if (rs != null) {
                        do {
                            ObjectNode jsonObj = factory.objectNode();
                            jsonObj.put("attr_id", rs.getLong("id"));
                            jsonObj.put("name", rs.getString("name"));
                            jsonObj.put("ref_id", rs.getLong("ref_id"));
                            jsonObj.put("value", rs.getString("value"));
                            jsonObj1.add(jsonObj);
                        } while (rs.next());
                    }
                    return jsonObj1;
                }
            }, id);
            rootNode.put("references", ref);
        } catch (EmptyResultDataAccessException e){
           e.printStackTrace();
        }

        return rootNode;
    }
}
