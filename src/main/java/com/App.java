package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App{

    private static final String url = "jdbc:postgresql://localhost/postgres";
    private static final String user = "postgres";
    private static final String password = "cisco";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) throws SQLException, JsonProcessingException {

        App app = new App();
        app.connect();

        Connection conn = DriverManager.getConnection(url, user, password);
        PostgresGroupDao DaoFirst = new PostgresGroupDao(conn);

        //DaoFirst.delete(1);
        DaoFirst.create();
        Object object_test = DaoFirst.read(2);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(object_test);
        System.out.println("json " + jsonString);

        //com.Object g = DaoFirst.read(1);
        //System.out.print(g.getName());
    }
}