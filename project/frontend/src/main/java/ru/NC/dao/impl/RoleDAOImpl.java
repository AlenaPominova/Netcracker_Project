package ru.NC.dao.impl;

import org.hibernate.Session;
import ru.NC.dao.IDAO;
import ru.NC.models.UserRole;

import javax.management.Query;
import java.util.List;

public class RoleDAOImpl implements IDAO<UserRole> {
    Session session;

    public List<UserRole> findAll() {
        return session.createQuery("FROM UserRole ").list();
    }

    public UserRole findById(Long id) {
        return session.load(UserRole.class, id);
    }

    public void save(UserRole object) {
        session.save(object);
    }

    public void update(UserRole object) {
        session.update(object);
    }

    public void delete(UserRole object) {
        session.delete(object);
    }
}
