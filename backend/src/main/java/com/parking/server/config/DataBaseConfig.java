package com.parking.server.config;

import com.parking.server.dao.PersistException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
* Настенька, вытаскивай это из .properties
* */
public class DataBaseConfig {

    private String user = "postgres";
    private String password = "Ithkjr25";
    private String url = "jdbs:postgresql://localhost:5433/parkingdb";
    private String driver = "org.postgresql.Driver";

    public Connection getContext() throws PersistException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return connection;
    }

    public DataBaseConfig() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}