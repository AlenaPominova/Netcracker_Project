package com.site.mapper;

import com.site.objects.Pojo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjMapper implements RowMapper<Pojo> {
    public Pojo mapRow(ResultSet resultSet, int i) throws SQLException{
        Pojo pojo = new Pojo();
        pojo.setId(resultSet.getLong("object_id"));
        pojo.setName(resultSet.getString("name"));
        pojo.setType_id(resultSet.getLong("object_type_id"));
        pojo.setParent_id(resultSet.getLong("parent_id"));
        return pojo;
    }
}
