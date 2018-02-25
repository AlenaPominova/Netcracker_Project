package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresGroupDao implements GroupDao {

    private final Connection connection;

    public void create() throws SQLException {
        String sql = "INSERT INTO objects DEFAULT VALUES";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.execute();
    }

    public com.Object read(int key) throws SQLException {
        String sql = "SELECT * FROM objects WHERE object_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setInt(1, key);

        ResultSet rs = stm.executeQuery();
        rs.next();
        Object g = new Object();
        g.setId(rs.getInt("object_id"));
        g.setName(rs.getString("name"));
        g.setObject_type_id(rs.getInt("object_type_id"));
        return g;
    }

    public void update(Object group) {

    }

    public void delete(int key) throws SQLException {
        String sql = "DELETE FROM objects WHERE object_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, key);
    }


    public PostgresGroupDao(Connection connection) {
        this.connection = connection;
    }
}