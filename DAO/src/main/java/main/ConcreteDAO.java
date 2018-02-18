package main;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ConcreteDAO implements myDAO {
	private static JdbcTemplate jdbcTemplateObject;
	static final Logger log = LogManager.getRootLogger();

	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void create(Pojo obj) {
		jdbcTemplateObject.update("INSERT INTO public.\"OBJECTS\" "+
									 "(object_id,object_type_id,owner_id, name, description)"+
								     "VALUES "+
								     "("+obj.getId()+","+obj.getTypeId()+","+
								     obj.getOwnerId()+","+"'"+obj.getName()+"'"+","+obj.getDescription()+")");
		if(obj.getValues()!=null)
			for(Map.Entry<Long, String> entry : obj.getValues().entrySet()) {
				jdbcTemplateObject.update("INSERT INTO public.\"PARAMS\" "+
						 "(attr_id,object_id,value)"+
					     "VALUES "+
					     "("+entry.getKey()+","+obj.getId()+","+
					     "'"+entry.getValue()+"'"+")");
			}
		if(obj.getListValues()!=null)
			for(Map.Entry<Long, Long> entry : obj.getListValues().entrySet()) {
				jdbcTemplateObject.update("INSERT INTO public.\"PARAMS\" "+
						 "(attr_id,object_id,list_value_id)"+
					     "VALUES "+
					     "("+entry.getKey()+","+obj.getId()+","+
					     entry.getValue()+")");
			}
		if(obj.getDate()!=null)
			for(Map.Entry<Long, Timestamp> entry : obj.getDate().entrySet()) {
				jdbcTemplateObject.update("INSERT INTO public.\"PARAMS\" "+
						 "(attr_id,object_id,date_value)"+
					     "VALUES "+
					     "("+entry.getKey()+","+obj.getId()+","+
					     "'"+entry.getValue()+"'"+")");
			}
		if(obj.getReference()!=null)
			for(Map.Entry<Long, Long> entry : obj.getReference().entrySet()) {
				jdbcTemplateObject.update("INSERT INTO public.\"REFERENCES\" "+
						 "(attr_id,reference,object_id)"+
					     "VALUES "+
					     "("+entry.getKey()+","+entry.getValue()+","+
					     obj.getId()+")");
			}
		log.info("The object was inserted");
	}



	public Pojo read(long id) {
		log.info("Reading the object with id"+id);
		Pojo obj=getCommonInfo(id);
		obj.setValues(getValues(id));
		obj.setDate(getDate(id));
		obj.setListValues(getListValues(id));
		obj.setReference(getReferences(id));
	    return obj;
	}

	private Pojo getCommonInfo(long id){
	      String sql = "select * from \"OBJECTS\" where object_id = "+id;
	      return jdbcTemplateObject.queryForObject(sql, (rs,rowNum)->{
	  		Pojo obj = new Pojo(rs.getLong("object_type_id"),rs.getString("name"));
			obj.setId(rs.getLong(("object_id")));
			Long val=rs.getLong("owner_id");
			if(val!=null)
				obj.setOwnerId(val);
			obj.setDescription(rs.getString("description"));
		    return obj;
	      });
	}

	private Map<Long,String> getValues(long id){
		String sql = "SELECT ATTR_ID,VALUE "+
						"FROM    \"PARAMS\"  "+
						"WHERE OBJECT_ID = "+id;
		try{
		return jdbcTemplateObject.queryForObject(sql,(rs,rowNum)->{
			Map<Long, String> values=new HashMap<Long,String>();
			do
				if(rs.getString("value")!=null)
				values.put(rs.getLong("attr_id"), rs.getString("value"));
			while (rs.next());
		    return values;

		});
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	private Map<Long,Timestamp> getDate(long id){
		String sql = "SELECT attr_id,date_value "+
						"FROM    \"PARAMS\" "+
						"where object_id="+id;
		try{
		return jdbcTemplateObject.queryForObject(sql, (rs,rowNum)->{
			Map<Long, Timestamp> values=new HashMap<Long,Timestamp>();
			do
				if(rs.getString("date_value")!=null)
					values.put(rs.getLong("attr_id"), rs.getTimestamp("date_value"));
			while(rs.next());
		    return values;
		});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	private Map<Long,Long> getListValues(long id){
		String sql = "SELECT * "+
						"FROM \"PARAMS\" "+
						"WHERE OBJECT_ID="+id+" and list_value_id is not null";
		try{
		return jdbcTemplateObject.queryForObject(sql, (rs,rowNum)->{
			Map<Long, Long> values=new HashMap<Long,Long>();
			do
				if(rs.getString("list_value_id")!=null)
				values.put(rs.getLong("attr_id"), rs.getLong("list_value_id"));
			while(rs.next());
		    return values;
		});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	private Map<Long, Long> getReferences(long id){
		String sql = "SELECT * "+
						"FROM \"REFERENCES\" "+
						"WHERE OBJECT_ID="+id;
		try{
		return jdbcTemplateObject.queryForObject(sql, (rs,rowNum)->{
			Map<Long, Long> values=new HashMap<Long,Long>();
			do
				values.put(rs.getLong("attr_id"), rs.getLong("reference"));
			while(rs.next());
		    return values;
		});
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	public void delete(long id) {
		String sql = "delete from \"OBJECTS\" where object_id = "+id;
		jdbcTemplateObject.update(sql);
		log.info("The object "+id+" was deleted");
	}

	public void update(Pojo obj) {
		for(Map.Entry<Long, String> entry : obj.getValues().entrySet()) {
			jdbcTemplateObject.update("INSERT INTO public.\"PARAMS\" "+
									 "(attr_id,object_id,value)"+
								     "VALUES "+
								     "("+entry.getKey()+","+obj.getId()+","+
								     "'"+entry.getValue()+"'"+")"
								     + "ON CONFLICT (OBJECT_ID,ATTR_ID) DO UPDATE SET "+
								     " value="+"'"+entry.getValue()+"'");
		}

		for(Map.Entry<Long, Long> entry : obj.getListValues().entrySet()) {
			jdbcTemplateObject.update("INSERT INTO public.\"PARAMS\" "+
					 "(attr_id,object_id,list_value_id)"+
				     "VALUES "+
				     "("+entry.getKey()+","+obj.getId()+","+
				     +entry.getValue()+")"
				     + "ON CONFLICT (attr_id,object_id) DO UPDATE SET "+
				     "value="+entry.getValue());
		}

		for(Map.Entry<Long, Timestamp> entry : obj.getDate().entrySet()) {
			jdbcTemplateObject.update("INSERT INTO public.\"PARAMS\" "+
					 "(attr_id,object_id,date_value)"+
				     "VALUES "+
				     "("+entry.getKey()+","+obj.getId()+","+
				     "'"+entry.getValue()+"'"+")"
				     + "ON CONFLICT (OBJECT_ID,ATTR_ID) DO UPDATE SET "+
				     "date_value="+"'"+entry.getValue()+"'");
		}

		for(Map.Entry<Long, Long> entry : obj.getReference().entrySet()) {

			jdbcTemplateObject.update("INSERT INTO public.\"REFERENCES\" "+
					 "(attr_id,object_id,reference)"+
				     "VALUES "+
				     "("+entry.getKey()+","+obj.getId()+","
				     +entry.getValue().toString()+")"
				     + "ON CONFLICT (OBJECT_ID,ATTR_ID) DO UPDATE SET "
				     +"attr_id="+entry.getKey()+
				     ", value="+"'"+entry.getValue().toString()+"'");
		}

		log.info("The object "+obj.getId()+" was updated");
	}

	public static String getAttrName(long id){
		String sql = "SELECT NAME "+
				"FROM \"ATTRIBUTES\" "+
				"WHERE ATTR_ID="+id;
		return jdbcTemplateObject.queryForObject(sql, (rs,rowNum)->{
			String name=rs.getString("name");
		    return name;
		});
	}
	public static String getObjectName(Long id){
		String sql = "SELECT NAME "+
				"FROM \"OBJECTS\" "+
				"WHERE OBJECT_ID="+id;
		return jdbcTemplateObject.queryForObject(sql, (rs,rowNum)->{
			String name=rs.getString("name");
		    return name;
		});
	}
	public long getNewIdFromSQL(){
	      String sql = "select generate_id()";
	      return jdbcTemplateObject.queryForObject(sql, (rs,rowNum)->{
		    return rs.getLong("generate_id");
	      });
	}

}
