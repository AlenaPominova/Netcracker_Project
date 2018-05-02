package com.parking.server.parser;

import com.parking.server.dao.PersistException;
import com.parking.server.objects.Pojo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParseForDao {

    public List<Pojo> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Pojo> result = new LinkedList<Pojo>();
        try {
            while (rs.next()) {
                Pojo p = new Pojo(rs.getInt("object_id"),
                        rs.getString("name"),
                        rs.getInt("object_type_id"));
                result.add(p);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }


    public Pojo parseResultSetParams(ResultSet rs) throws PersistException {
        //LinkedList<Pojo> result = new LinkedList<Pojo>();
        Pojo p = null;
        try {
            Map<Long, String> map_value = new HashMap<>();
            Map<Long, Long> map_list_value = new HashMap<>();
            Map<Long, Timestamp> map_date = new HashMap<>();
            //Map<Long, Long> map_reference = new HashMap<>();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            while (rs.next()) {
                p = new Pojo(rs.getInt("object_id"),
                        rs.getString("name"),
                        rs.getInt("object_type_id"));
                if (rs.getString("value") != null) {
                    map_value.put(rs.getLong("attr_id"), rs.getString("value"));
                }
                p.setValues(map_value);
                if (rs.getString("list_value_id") != null) {
                    map_list_value.put(rs.getLong("attr_id"), rs.getLong("list_value_id"));
                }
                p.setListValue(map_list_value);
                if (rs.getString("date_value") != null) {
                    String target = rs.getString("date_value");
                    Date date = format.parse(target);
                    map_date.put(rs.getLong("attr_id"), new Timestamp(date.getTime()));
                }
                p.setDate(map_date);
                /*if (rs.getString("reference") != null) {
                    map_reference.put(rs.getLong("object_id"), rs.getLong("reference"));
                }
                p.setReferences(map_reference);*/
            }
            //result.add(p);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return p;
    }

    public void parseForUpdate(PreparedStatement st, Long object_id, Long attr_id, String value) {
        try {
            st.setString(1, value);
            st.setLong(2, object_id);
            st.setLong(3, attr_id);
            st.setLong(4, object_id);
            st.setString(5, value);
            st.setLong(6, attr_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void qreate_object(PreparedStatement st, Pojo pojo) {
        try {
            st.setLong(1, pojo.getId());
            st.setLong(2, pojo.getType_id());
            st.setString(3, pojo.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert_params(PreparedStatement st, Pojo pojo) {
        try {
            for (Map.Entry entry : pojo.getValues().entrySet()) {
                st.setLong(1, (Long) entry.getKey());
                st.setLong(2, pojo.getId());
                st.setString(3, entry.getValue().toString());
                st.setString(4, "null");
            }

            for (Map.Entry entry : pojo.getDate().entrySet()) {
                st.setLong(1, (Long) entry.getKey());
                st.setLong(2, pojo.getId());
                st.setString(3, "null");
                st.setString(4, entry.getValue().toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert_ref(PreparedStatement st, Pojo pojo) {
        try {
            for (Map.Entry entry : pojo.getReferences().entrySet()) {
                st.setLong(1, (Long) entry.getKey());
                st.setLong(2, (Long) entry.getValue());
                st.setLong(3, pojo.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void perse_referenses(ResultSet rs, Pojo p) throws PersistException {
        try {
            Map<Long, Long> map_reference = new HashMap<>();
            p.setReferences(map_reference);
            while (rs.next()) {
                if (rs.getString("reference") != null) {
                    map_reference.put(rs.getLong("attr_id"), rs.getLong("reference"));
                }
                p.setReferences(map_reference);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
