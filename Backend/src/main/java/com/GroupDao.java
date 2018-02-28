package com;

import java.sql.SQLException;

/** Объект для управления персистентным состоянием объекта Group */
public interface GroupDao {

    /** Создает новую запись и соответствующий ей объект */
    public void create() throws SQLException;

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
    java.lang.Object read(int key) throws SQLException;

    /** Сохраняет состояние объекта group в базе данных */
    public void update(Object group);

    /** Удаляет запись об объекте из базы данных */
    public void delete(int key) throws SQLException;

}