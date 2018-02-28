package com;

import com.DaoFactory;
import com.GroupDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDaoFactory implements DaoFactory {

    private String user = "postgres";
    private String password = "cisco";
    private String url = "jdbc:postgresql://localhost/postgres";
    //private String driver = "com.mysql.jdbc.Driver";//Имя драйвера

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public GroupDao getGroupDao(Connection connection) {
        return null;
    }

    //    public MySqlDaoFactory() {
//        try {
//            Class.forName(driver);//Регистрируем драйвер
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}

