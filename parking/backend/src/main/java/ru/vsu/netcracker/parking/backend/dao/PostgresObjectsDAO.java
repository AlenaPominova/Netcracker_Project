package ru.vsu.netcracker.parking.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.vsu.netcracker.parking.backend.objects.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PostgresObjectsDAO implements ObjectsDAO {


    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    Connection connection = null;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void delete(int objId) {

    }

    @Override
    public String getParam(int objectId, int attributeId) {
        String query = PostgresSQLQueries.SELECT_ATTR_VALUE_QUERY;
        return this.jdbcTemplate.queryForObject(query, new Object[]{objectId, attributeId}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("value");
            }
        });
    }

    @Override
    public Map<Long, String> getAllParams(long objectId) {
        String query = PostgresSQLQueries.SELECT_ALL_PARAMS_BY_OBJECT_ID_QUERY;
        Map<Long, String> result = jdbcTemplate.query(query, new Object[]{objectId}, new ResultSetExtractor<Map<Long, String>>() {
            @Override
            public Map<Long, String> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                Map<Long, String> attrValues = new HashMap<>();
                while (resultSet.next()) {
                    attrValues.put(resultSet.getLong("attr_id"), resultSet.getString("value"));
                }
                return attrValues;
            }
        });
        return result;
    }

    @Override
    public Map<Long, Map<Long, String>> getAllByObjectType(long objectTypeId) {
        String query = PostgresSQLQueries.SELECT_ALL_BY_OBJECT_TYPE_QUERY;
        Map<Long, Map<Long, String>> result = jdbcTemplate.query(query, new Object[]{objectTypeId}, new ResultSetExtractor<Map<Long, Map<Long, String>>>() {
            @Override
            public Map<Long, Map<Long, String>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                Map<Long, Map<Long, String>> objectsAttrs = new HashMap<>();
                Map<Long, String> attrValues = new HashMap<>();
                long objectId = new Long(-1);
                while(resultSet.next()){
                    long curObjectId = resultSet.getLong("object_id");
                    if(curObjectId != objectId && objectId != -1){
                        objectsAttrs.put(objectId,attrValues);
                        attrValues = new HashMap<>();
                    }
                    attrValues.put(resultSet.getLong("attr_id"), resultSet.getString("value"));
                    objectId = curObjectId;
                }
                return objectsAttrs;

            }
        });
        return result;
    }

    @Override
    public void setParam(int attributeId, String value) {

    }
}
