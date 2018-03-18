package ru.NC.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import ru.NC.dao.impl.UserDAOImpl;
import ru.NC.json.UserParser;
import ru.NC.models.User;
import ru.NC.service.IService;

import java.util.List;

public class UserService implements IService {
    @Autowired
    private UserDAOImpl userDAO;
    @Autowired
    private UserParser userParser;

    public void fill(ArrayNode objectList) {
        List<User> users =  userParser.parseAll(objectList);
        for (User user: users) {
            userDAO.save(user);
        }
    }
}
