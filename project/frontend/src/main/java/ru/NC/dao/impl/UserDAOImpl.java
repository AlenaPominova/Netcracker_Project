package ru.NC.dao.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.NC.dao.IDAO;
import ru.NC.models.User;
import ru.NC.util.HibernateUtil;

import java.util.List;

public class UserDAOImpl implements IDAO<User> {
    private Session session;

    public List<User> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("FROM User").list();
        session.getTransaction().commit();
        return users;
    }

    public User findById(Long id) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User user = session.load(User.class, id);
        session.getTransaction().commit();
        return user;
    }

    public User findByEmail(String email) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "from User U"
                        + " where U.email = :email "
        )
                .setParameter("email", email);
        User user = (User) query.list();
        session.getTransaction().commit();
        return user;
    }

    public void save(User user) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    public void update(User user) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
    }

    public void delete(User object) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
    }
}
