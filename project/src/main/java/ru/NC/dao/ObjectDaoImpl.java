package ru.NC.dao;

import org.joda.time.LocalDate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.NC.models.Obj;
import ru.NC.models.Queries;

import javax.sql.DataSource;
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
     * @see ObjectDaoImpl#getValues(int)
     * @see ObjectDaoImpl#getDate(int)
     * @see ObjectDaoImpl#getReference(int)
     * @see Queries
     */
    public Obj read(final int id) {

        try {
            Obj object = jdbcTemplateObject.queryForObject(Queries.GET_OBJECT_INFO, new Object[]{id},
                    (resultSet, i) -> {

                        if (resultSet == null) return new Obj();

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
                    });

            object.setValues(getValues(id));
            object.setDate(getDate(id));
            object.setReference(getReference(id));
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
    private Map<Long, String> getValues(int id) {
        return jdbcTemplateObject.query(Queries.GET_VALUES, (rs) -> {
            Map<Long, String> map = new HashMap<>();
            while(rs.next()){
                map.put(rs.getLong("name"), rs.getString("value"));
            }
            return map;
        }, id, id);
    }

    /**
     * get only date_values (Map<id, value>) from Params table
     * @param id
     * @return Map<Long, LocalDate>
     * @see Queries
     */
    private Map<Long, LocalDate> getDate(int id) {
        return jdbcTemplateObject.query(Queries.GET_DATE, (rs) -> {
            Map<Long, LocalDate> map = new HashMap<>();
            while(rs.next()){
                map.put(rs.getLong("name"), new LocalDate(rs.getString("value")));
            }
            return map;
        }, id, id);
    }

    /**
     * get only references (Map<id, value>) from References table
     * @param id
     * @return Map<Long, String>
     * @see Queries
     */
    private Map<Long, String> getReference(int id) {
        return jdbcTemplateObject.query(Queries.GET_REFERENCE, (rs) -> {
            Map<Long, String> map = new HashMap<>();
            while(rs.next()){
                map.put(rs.getLong("name"), rs.getString("value"));
            }
            return map;
        }, id);
    }

    public String getParameter(int objId, int attributeId) {
        return jdbcTemplateObject.queryForObject(Queries.GET_PARAMETER,
                new Object[]{attributeId, attributeId, objId, objId}, String.class);
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
}
