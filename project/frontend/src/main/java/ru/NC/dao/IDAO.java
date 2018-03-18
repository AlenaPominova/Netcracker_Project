package ru.NC.dao;

import java.util.List;

public interface IDAO<T> {
    List<T> findAll();
    T findById(Long id);
    void save(T object);
    void update(T object);
    void delete(T object);
}
