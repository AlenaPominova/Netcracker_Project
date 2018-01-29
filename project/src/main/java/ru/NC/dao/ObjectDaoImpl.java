package ru.NC.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.LocalDate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.NC.models.Obj;
import ru.NC.models.Queries;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * DAO layer class of {@link Obj}
 * @author Alena Pominova
 * @version 1.0
 */
public class ObjectDaoImpl {

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
        System.out.println("in read");
        try {
            Obj object = jdbcTemplateObject.queryForObject(Queries.GET_OBJECT_INFO,
                    (resultSet, i) -> {

                        if (resultSet == null) {
                            System.out.println("null");
                            return new Obj();
                        }
                        System.out.println("ne null");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        Long parentId = resultSet.getLong("parent_id");
                        Long typeId = resultSet.getLong("type_id");

                        Obj obj = new Obj();
                        obj.setId(id);
                        obj.setName(name);
                        obj.setDescription(description);
                        obj.setParentId(parentId);
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
        Object[] params = new Object[]{object.getId(), object.getName(), object.getDescription(), object.getTypeId()};
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
        Object[] params = new Object[]{object.getName(), object.getDescription(),
                object.getTypeId(), object.getId()};
        jdbcTemplateObject.update(Queries.UPDATE_OBJECT, params);

        if (object.getValues() != null) {
            for (Map.Entry entry : object.getValues().entrySet()) {
                jdbcTemplateObject.update(Queries.SET_PARAMETER, entry.getValue(), entry.getKey(), object.getId());
            }
        }

        if (object.getDate() != null) {
            for (Map.Entry entry : object.getDate().entrySet()) {
                jdbcTemplateObject.update(Queries.SET_DATE, entry.getValue(), entry.getKey(), object.getId());
            }
        }

        if (object.getReference() != null) {
            for (Map.Entry entry : object.getReference().entrySet()) {
                jdbcTemplateObject.update(Queries.SET_REFERENCE, entry.getValue(), entry.getKey(), object.getId());
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
     * @return Map<Long, LocalDate>
     * @see Queries
     */
    private Map<Long, LocalDate> getDate(long typeId, long id) {
        return jdbcTemplateObject.query(Queries.GET_DATE, (rs) -> {
            Map<Long, LocalDate> map = new HashMap<>();
            while(rs.next()){
                map.put(rs.getLong("name"), new LocalDate(rs.getString("value")));
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
        jdbcTemplateObject.update(Queries.SET_PARAMETER, value, attributeId, objId);
    }

    public void setReference(int objId, int attributeId, Map<Long, String> value) {
        jdbcTemplateObject.update(Queries.SET_REFERENCE, value, attributeId, objId);
    }

    public void setDate(int objId, int attributeId, Map<Long, String> value) {
        jdbcTemplateObject.update(Queries.SET_DATE, value, attributeId, objId);
    }

    /**
     * method creates json by object`s id
     * @param id
     * @return JsonNode
     * @see Queries
     */
    public JsonNode getObjectAsJson(long id) {
        JsonNodeFactory factory = new JsonNodeFactory(false);

        JsonNode jsonObject = factory.objectNode();;
        try {
            jsonObject = jdbcTemplateObject.queryForObject(Queries.GET_OBJECT_INFO, new RowMapper<JsonNode>() {
                @Nullable
                @Override
                public JsonNode mapRow(ResultSet rs, int i) throws SQLException {
                    JsonNode jsonObj = factory.objectNode();
                    ((ObjectNode) jsonObj).put("name", rs.getString("name"));
                    ((ObjectNode) jsonObj).put("parent_id", rs.getLong("parent_id"));
                    ((ObjectNode) jsonObj).put("type_id", rs.getLong("type_id"));

                    return jsonObj;
                }
            }, id);
            ((ObjectNode) jsonObject).put("Id", id);
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
                        JsonNode jsonObj = factory.objectNode();
                        ((ObjectNode) jsonObj).put("id", rs.getLong("id"));
                        ((ObjectNode) jsonObj).put("name", rs.getString("name"));
                        ((ObjectNode) jsonObj).put("value", rs.getString("value"));
                        jsonObj1.add(jsonObj);
                    }
                    return jsonObj1;
                }
            }, id);
        } catch (EmptyResultDataAccessException e){
            e.printStackTrace();
        }

        JsonNode date;
        try {
            date = jdbcTemplateObject.queryForObject(Queries.GET_DATE_JSON, new RowMapper<JsonNode>() {
                @Nullable
                @Override
                public JsonNode mapRow(ResultSet rs, int i) throws SQLException {

                    JsonNode jsonObj = factory.objectNode();
                    ((ObjectNode) jsonObj).put("id", rs.getLong("id"));
                    ((ObjectNode) jsonObj).put("name", rs.getString("name"));
                    ((ObjectNode) jsonObj).put("value", rs.getString("value"));

                    return jsonObj;
                }
            }, id);
            values.add(date);
            ((ObjectNode) jsonObject).put("values", values);
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
                    //while(rs.next()){
                    JsonNode jsonObj = factory.objectNode();
                    ((ObjectNode) jsonObj).put("attr_id", rs.getLong("id"));
                    ((ObjectNode) jsonObj).put("name", rs.getString("name"));
                    ((ObjectNode) jsonObj).put("ref_id", rs.getLong("ref_id"));
                    ((ObjectNode) jsonObj).put("value", rs.getString("value"));
                    jsonObj1.add(jsonObj);
                    // }
                    return jsonObj1;
                }
            }, id);
            ((ObjectNode) jsonObject).put("references", ref);
        } catch (EmptyResultDataAccessException e){
            e.printStackTrace();
        }

        return jsonObject;
    }
}
