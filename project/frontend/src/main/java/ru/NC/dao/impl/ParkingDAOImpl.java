package ru.NC.dao.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.NC.dao.IDAO;
import ru.NC.models.Parking;
import ru.NC.util.HibernateUtil;

import java.util.List;

public class ParkingDAOImpl implements IDAO<Parking> {
    private Session session;

    public List<Parking> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Parking> parkings = session.createQuery("FROM Parking ").list();
        session.getTransaction().commit();
        return parkings;
    }

    public Parking findById(Long id) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Parking parking = session.load(Parking.class, id);
        session.getTransaction().commit();
        return parking;
    }

    public Parking findByAddress(String address){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "from Parking P"
                        + " where P.address = :address "
        )
                .setParameter("address", address);
        Parking parking = (Parking) query.list();
        session.getTransaction().commit();
        return parking;
    }

    public Parking findByCoordinates(double longitude, double latitude){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "from Parking P"
                        + " where P.longitude = :longitude and P.latitude = :latitude "
        )
                .setParameter("longitude", longitude)
                .setParameter("latitude", latitude);
        Parking parking = (Parking) query.list();
        session.getTransaction().commit();
        return parking;
    }

    public void save(Parking object) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
    }

    public void update(Parking object) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
    }

    public void delete(Parking object) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
    }
}
