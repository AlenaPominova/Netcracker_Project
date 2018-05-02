package com.parking.server.dao;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.parking.server.objects.Pojo;
import com.parking.server.parser.ParseForDao;
import com.parking.server.read_json.ReadJson;
import com.parking.server.sql.SqlObjectDao;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class JDBCDao implements GenericDao {

    private Connection connection;
    final SqlObjectDao sqlObjectDao = new SqlObjectDao();
    final ParseForDao parser = new ParseForDao();
    final ReadJson readJson = new ReadJson();

    public JDBCDao(Connection connection) throws PersistException {
        this.connection = connection;
    }

    @Override
    public void create(JsonNode jsonNode) throws PersistException {
        String sql = null;
        try {
            sql = readJson.readJsonForCreate(jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void create(Pojo pojo) throws PersistException, IOException {
        //String sql = null;
        String sql_object = sqlObjectDao.getCreateQueryNormas();
        String sql_params = sqlObjectDao.getInsertQueryNormas();
        String sql_ref = sqlObjectDao.getCreateReferencesQueryNormas();
        try (PreparedStatement statement = connection.prepareStatement(sql_object)) {
            parser.qreate_object(statement, pojo);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement(sql_params)) {
            parser.insert_params(statement, pojo);
            statement.executeUpdate();
        }
        catch (Exception e) {
            throw new PersistException(e);
        }
        try (PreparedStatement statement = connection.prepareStatement(sql_ref)) {
            parser.insert_ref(statement, pojo);
            statement.executeUpdate();
        }
        catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Pojo getByPK(Long key) throws PersistException {
        //List<Pojo> list;
        Pojo p = null;
        String sql = sqlObjectDao.getFindByPKQuery();
        String sql_ref = sqlObjectDao.get_ref();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, key);
            ResultSet rs = statement.executeQuery();
            //list = parser.parseResultSetParams(rs);
            p = parser.parseResultSetParams(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }

        try (PreparedStatement statement = connection.prepareStatement(sql_ref)) {
            statement.setLong(1, key);
            ResultSet rs = statement.executeQuery();
            parser.perse_referenses(rs, p);
            //list = parser.parseResultSetParams(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        /*if (list == null || list.size() == 0) {
            throw new PersistException("Record with PK = " + key + " not found.");
        }
        return list.iterator().next();*/
        return p;
    }

    @Override
    public void update(JsonNode jsonNode) throws PersistException, IOException {
        Pojo new_p = readJson.readJsonforUpdate(jsonNode);
        Pojo p = getByPK(new_p.getId());

        for (Map.Entry entry : p.getValues().entrySet()) {
            for (Map.Entry new_entry : new_p.getValues().entrySet()) {
                if (entry.getKey().equals(new_entry.getKey()) && !entry.getValue().equals(new_entry.getValue())) {
                    prepareForUpdate(p.getId(), Long.valueOf(String.valueOf(entry.getKey())), String.valueOf(new_entry.getValue()));
                }
            }
        }
    }

    @Override
    public void update(Pojo pojo) throws PersistException, IOException {
        Pojo p = getByPK(pojo.getId());

        for (Map.Entry entry : p.getValues().entrySet()) {
            for (Map.Entry new_entry : pojo.getValues().entrySet()) {
                if (entry.getKey().equals(new_entry.getKey()) && !entry.getValue().equals(new_entry.getValue())) {
                    prepareForUpdate(p.getId(), Long.valueOf(String.valueOf(entry.getKey())), String.valueOf(new_entry.getValue()));
                }
            }
        }
    }

    @Override
    public void delete(Long id) throws PersistException {
        String sql = sqlObjectDao.getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, id);
            } catch (Exception e) {
                throw new PersistException(e);
            }
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public List<Pojo> getAll() throws PersistException {
        List<Pojo> list;
        String sql = sqlObjectDao.getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parser.parseResultSet(rs);
            for (Pojo p : list) {
                p.setValues(getByPK(p.getId()).getValues());
                p.setListValue(getByPK(p.getId()).getListValue());
                p.setDate(getByPK(p.getId()).getDate());
                p.setReferences(getByPK(p.getId()).getReferences());
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    @Override
    public List<JsonNode> get_parking() throws PersistException {
        List<Pojo> list;
        String sql = sqlObjectDao.get_parking();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parser.parseResultSet(rs);
            for (Pojo p : list) {
                p.setValues(getByPK(p.getId()).getValues());
                p.setListValue(getByPK(p.getId()).getListValue());
                p.setDate(getByPK(p.getId()).getDate());
                p.setReferences(getByPK(p.getId()).getReferences());
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        List<JsonNode> json_list = new LinkedList<>();
        for (Pojo p : list) {
            try {
                json_list.add(writeJson(p.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return json_list;
    }

    @Override
    public List<Pojo> get_parking_pojo() throws PersistException {
        List<Pojo> list;
        String sql = sqlObjectDao.get_parking();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parser.parseResultSet(rs);
            for (Pojo p : list) {
                p.setValues(getByPK(p.getId()).getValues());
                p.setListValue(getByPK(p.getId()).getListValue());
                p.setDate(getByPK(p.getId()).getDate());
                p.setReferences(getByPK(p.getId()).getReferences());
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }


    @Override
    public List<JsonNode> writeAllJsons() throws IOException, PersistException {
        List<Pojo> list = getAll();
        List<JsonNode> json_list = new LinkedList<>();
        for (Pojo p : list) {
            json_list.add(writeJson(p.getId()));
        }
        return json_list;
    }


    @Override
    public JsonNode writeJson(Long id) throws IOException, PersistException {

        JsonNodeFactory factory = new JsonNodeFactory(false);
        JsonNode object = factory.objectNode();

        Pojo p = getByPK(id);
        ((ObjectNode) object).put("id", p.getId());
        ((ObjectNode) object).put("name", p.getName());
        ((ObjectNode) object).put("type_id", p.getType_id());
        Map<Long, String> map = getNameAttr();

        ArrayNode values = factory.arrayNode();
        for (Map.Entry entry : p.getValues().entrySet()) {
            ObjectNode value = factory.objectNode();
            value.put("id", entry.getKey().toString());
            value.put("name", map.get(entry.getKey()));
            value.put("value", entry.getValue().toString());
            values.add(value);
        }
        ArrayNode list_values = factory.arrayNode();
        for (Map.Entry entry : p.getListValue().entrySet()) {
            ObjectNode value = factory.objectNode();
            value.put("id", entry.getKey().toString());
            value.put("value", entry.getValue().toString());
            list_values.add(value);
        }
        ArrayNode dates = factory.arrayNode();
        for (Map.Entry entry : p.getDate().entrySet()) {
            ObjectNode value = factory.objectNode();
            value.put("id", entry.getKey().toString());
            value.put("name", map.get(entry.getKey()));
            value.put("value", entry.getValue().toString());
            dates.add(value);
        }
        ArrayNode references = factory.arrayNode();
        for (Map.Entry entry : p.getReferences().entrySet()) {
            ObjectNode value = factory.objectNode();
            value.put("id", entry.getKey().toString());
            value.put("value", entry.getValue().toString());
            references.add(value);
        }
        ((ObjectNode) object).put("values", values);
        ((ObjectNode) object).put("date", dates);
        ((ObjectNode) object).put("list_values", list_values);
        ((ObjectNode) object).put("references", references);

        return object;
    }

    public Map<Long, String> getNameAttr() throws PersistException {
        Map<Long, String> map = new HashMap<>();
        String sql = sqlObjectDao.getNameAttr();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                map.put(rs.getLong("attr_id"), rs.getString("name"));
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return map;
    }


    /*
     * Настенька, тут case 4 предполагает значение free/don't для паковки,
     * поменяй, если в бд по-другому сделаете
     * */
    public void prepareForUpdate(Long object_id, Long attr_id, String value) {
        Integer attr_type = null;
        try (PreparedStatement statement = connection.prepareStatement(sqlObjectDao.getAttrTypeQuery())) {
            statement.setLong(1, attr_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                attr_type = rs.getInt("attr_type_id");
            }
            switch (attr_type) {
                case 1:
                case 2:
                    try (PreparedStatement st = connection.prepareStatement(sqlObjectDao.getUpdateValuesQuery())) {
                        parser.parseForUpdate(st, object_id, attr_id, value);
                        st.executeUpdate();
                    }
                    break;
                case 3:
                    try (PreparedStatement st = connection.prepareStatement(sqlObjectDao.getUpdateDatesQuery())) {
                        parser.parseForUpdate(st, object_id, attr_id, value);
                        st.executeUpdate();
                    }
                    break;
                case 4: //attr_type_id of references in table "ATTRIBUTES"
                    try (PreparedStatement st = connection.prepareStatement(sqlObjectDao.getUpdateList_values_idQuery())) {
                        parser.parseForUpdate(st, object_id, attr_id, value);
                        st.executeUpdate();
                    }
                    break;
                default:
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
