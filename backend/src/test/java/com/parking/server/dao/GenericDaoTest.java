package com.parking.server.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.server.config.DataBaseConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/*
* Настенька, напиши, если нужно будет
* */
public class GenericDaoTest {

    JDBCDao dao;

    @Before
    public void setUp() throws Exception {
        DataBaseConfig s = new DataBaseConfig();
        dao = new JDBCDao(s.getContext());
    }

    @After
    public void tearDown() throws Exception {
        dao = null;
    }

    private JsonNode initListWithDefaultValues() throws IOException {
        String s = "{\"id\":130,\"name\":\"User130\",\"type_id\":2,\"values\":[{\"id\":\"201\",\"value\":\"IvanovIvan\"},{\"id\":\"202\",\"value\":\"89204556553\"},{\"id\":\"203\",\"value\":\"mail@yandex.ru\"},{\"id\":\"204\",\"value\":\"12345\"}],\"date\":[{\"id\":\"206\",\"value\":\"1996-07-24 00:00:00.0\"}],\"list_values\":[],\"references\":[{\"id\":\"100\",\"value\":\"3\"}]}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(s, JsonNode.class);
        return jsonNode;
    }

    /*
    * Настенька, не ну я хз, как их сравнивать, если id произвольно генерятся
    * */
    @org.junit.Test
    public void create() throws PersistException, IOException {
        /*dao.create(initListWithDefaultValues());
        Long expected = Long.valueOf(???);
        Long actual = dao.getByPK(Long.valueOf(???)).getId();
        assertEquals(expected, actual);*/
    }

    @org.junit.Test
    public void getByPK() {
    }

    @org.junit.Test
    public void update() {
    }

    @org.junit.Test
    public void delete() {
    }

    @org.junit.Test
    public void getAll() throws PersistException {
        List list = dao.getAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @org.junit.Test
    public void writeJson() {
    }
}

