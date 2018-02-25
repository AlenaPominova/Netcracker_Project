package com;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Controller
@RequestMapping("/hello")
public class HelloController {

    private static final String url = "jdbc:postgresql://localhost/postgres";
    private static final String user = "postgres";
    private static final String password = "cisco";

    @RequestMapping(method = RequestMethod.GET)public String printHello(ModelMap model) throws SQLException, JsonProcessingException {

        Connection conn = DriverManager.getConnection(url, user, password);
        PostgresGroupDao DaoFirst = new PostgresGroupDao(conn);

        DaoFirst.create();
        Object object_test = DaoFirst.read(3);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(object_test);

        model.addAttribute("message", jsonString);
        return "hello";
    }
}

