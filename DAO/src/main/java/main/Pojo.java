package main;

import java.sql.Timestamp;
import java.util.*;

public class Pojo {
	private long id;
	private long typeId;
	private String name;
	private String description;

    private Map<Long, String> values;
    private Map<Long, Long> listValues;
    private Map<Long, Timestamp> date;
    private Map<Long, Long> reference;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
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
	public Map<Long, String> getValues() {
		return values;
	}
	public void setValues(Map<Long, String> values) {
		if(values.size()!=0)
		this.values = values;
	}
	public Map<Long, Long> getListValue() {
		return listValues;
	}
	public void setListValue(Map<Long, Long> listValue) {
		if(listValue.size()!=0)
		this.listValues = listValue;
	}
	public Map<Long, Timestamp> getDate() {
		return date;
	}
	public void setDate(Map<Long, Timestamp> date) {
		if(date.size()!=0)
		this.date = date;
	}
	public Map<Long, Long> getReference() {
		return reference;
	}
	public void setReference(Map<Long, Long> reference) {
		if(reference.size()!=0)
		this.reference = reference;
	}
	@Override
	public String toString() {
		return "Pojo [id=" + id + ", typeId=" + typeId + ", name=" + name + ", description="
				+ description + ", values=" + values + ", listValue=" + listValues + ", date=" + date + ", reference="
				+ reference + "]";
	}



}
