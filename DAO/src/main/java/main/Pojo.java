package main;

import java.sql.Timestamp;
import java.util.*;

public class Pojo {
	private long object_id;
	private long object_type_id;
	private long parent_id;
	private String name;
	private String descrition;
	
    private Map<Long, String> values;
    private Map<Long, Long> listValue;
    private Map<Long, Timestamp> date;
    private Map<Long, Long> reference;
	public long getObject_id() {
		return object_id;
	}
	public void setObject_id(long object_id) {
		this.object_id = object_id;
	}
	public long getObject_type_id() {
		return object_type_id;
	}
	public void setObject_type_id(long object_type_id) {
		this.object_type_id = object_type_id;
	}
	public long getParent_id() {
		return parent_id;
	}
	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescrition() {
		return descrition;
	}
	public void setDescrition(String descrition) {
		this.descrition = descrition;
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
	public String toString() {
		return "Pojo [object_id=" + object_id + ", object_type_id=" + object_type_id + ", parent_id=" + parent_id
				+ ", name=" + name + ", descrition=" + descrition + ", values=" + values + ", listValue=" + listValue
				+ ", date=" + date + ", reference=" + reference + "]";
	}
    
   
}
