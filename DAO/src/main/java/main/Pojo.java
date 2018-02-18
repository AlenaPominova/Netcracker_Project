package main;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

public class Pojo {
	private long id;
	private long typeId;
	private long ownerId;
	private String name;
	private String description;
	private Map<Long, String> values;
    private Map<Long, Long> listValues;
    private Map<Long, Timestamp> date;
    private Map<Long, Long> reference;
	public Pojo(long typeId, String name) {
		super();
		this.typeId = typeId;
		this.name = name;
	}
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
	public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
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
		this.values = values;
	}
	public Map<Long, Long> getListValues() {
		return listValues;
	}
	public void setListValues(Map<Long, Long> listValues) {
		this.listValues = listValues;
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
		return "Pojo [id=" + id + ", typeId=" + typeId + ", ownerId=" + ownerId + ", name=" + name + ", description="
				+ description + ", values=" + values + ", listValues=" + listValues + ", date=" + date + ", reference="
				+ reference + "]";
	}


}