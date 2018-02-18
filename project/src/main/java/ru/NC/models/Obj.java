package ru.NC.models;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Class of person with fields: <b>id</b> - autogenerate,
 * <b>values</b> - hold values from Params table,
 * <b>listValue</b> - hold list_values from List_values table,
 * <b>date</b> - hold date_values from Params table,
 * <b>reference</b> - hold date_values from References table
 *@author Alena Pominova
 *@version 1.0
 */
public class Obj {

    private long id;
    private String name;
    private String description;
    private long typeId;
    private Map<Long, String> values;
    private Map<Long, Long> listValue;
    private Map<Long, Timestamp> date;
    private Map<Long, Long> reference;

    public Obj() {}

    /**
     * @param typeId
     * @param name
     * @see Obj#Obj()
     */
    public Obj(long typeId, String name){
        this.typeId = typeId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public Map<Long, String> getValues() {
        return values;
    }

    public void setValues(Map<Long, String> values) {
        this.values = values;
    }

    public Map<Long, Long> getListValue() {
        return listValue;
    }

    public void setListValue(Map<Long, Long> listValue) {
        this.listValue = listValue;
    }

    public Map<Long, Timestamp> getDate() {
        return date;
    }

    public void setDate(Map<Long, Timestamp> date) {
        this.date = date;
    }

    public Map<Long, Long> getReference() {
        return reference;
    }

    public void setReference(Map<Long, Long> reference) {
        this.reference = reference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Obj obj = (Obj) o;

        if (id != obj.id) return false;
        if (typeId != obj.typeId) return false;
        return name != null ? name.equals(obj.name) : obj.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (typeId ^ (typeId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return Obj.class.getSimpleName() +
                "{id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", typeId=" + typeId +
                ", values=" + values +
                ", listValue=" + listValue +
                ", date=" + date +
                ", reference=" + reference +
                '}';
    }
}
