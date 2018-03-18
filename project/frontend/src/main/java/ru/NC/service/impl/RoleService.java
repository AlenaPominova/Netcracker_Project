package ru.NC.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import ru.NC.dao.impl.RoleDAOImpl;
import ru.NC.service.IService;

public class RoleService implements IService {
    @Autowired
    private RoleDAOImpl roleDAO;

    public void fill(ArrayNode objectList) {

    }
}
