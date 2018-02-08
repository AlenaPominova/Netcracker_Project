package Jackson;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import main.ConcreteDAO;
import main.Pojo;

public class MySerializer extends JsonSerializer<Pojo> {

	@Override
	public void serialize(Pojo obj, JsonGenerator jGen, SerializerProvider arg2) throws IOException {
	    jGen.writeStartObject();
	    jGen.writeStringField("name", obj.getName());
	    jGen.writeNumberField("parent_id", obj.getParent_id());
	    jGen.writeNumberField("type_id", obj.getObject_type_id());
	    jGen.writeNumberField("Id",obj.getObject_id());
	    if (obj.getValues().size()!=0) {
	    	jGen.writeArrayFieldStart("values");
	        	
	        	obj.getValues().forEach((k,v) -> {
					try {
						String name=ConcreteDAO.getAttrName(k);
						jGen.writeStartObject();
						jGen.writeStringField("attr_id", k+"");
						jGen.writeStringField("name", name);
						jGen.writeStringField("value", v);
						jGen.writeEndObject();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				});
	        jGen.writeEndArray();
	    }
	    if (obj.getDate().size()!=0) {
	    	jGen.writeArrayFieldStart("date_values");
	        	
	        	obj.getDate().forEach((k,v) -> {
					try {
						String name=ConcreteDAO.getAttrName(k);
						jGen.writeStartObject();
						jGen.writeStringField("attr_id", k+"");
						jGen.writeStringField("name", name);
						jGen.writeStringField("value", v.toString());
						jGen.writeEndObject();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				});
	        jGen.writeEndArray();
	    }
	    if (obj.getListValue().size()!=0) {
	    	jGen.writeArrayFieldStart("list_values");
	        	obj.getListValue().forEach((k,v) -> {
					try {
						String name=ConcreteDAO.getAttrName(k);
						String value=ConcreteDAO.getAttrName(k);
						jGen.writeStartObject();
						jGen.writeStringField("attr_id", k+"");
						jGen.writeStringField("name", name);
						jGen.writeStringField("list_value_id", v+"");
						jGen.writeEndObject();
					} catch (IOException e) {
						e.printStackTrace();
					}	
				});
	        jGen.writeEndArray();
	    }
	    if (obj.getReference().size()!=0) {
	    	jGen.writeArrayFieldStart("references");
	        	obj.getReference().forEach((k,v) -> {
					try {
						String attr_name=ConcreteDAO.getAttrName(k);
						String obj_name=ConcreteDAO.getObjectName(v);
						jGen.writeStartObject();
						jGen.writeStringField("attr_id", k+"");
						jGen.writeStringField("name", attr_name);
						jGen.writeStringField("ref", v+"");
						jGen.writeStringField("obj_name", obj_name);
						jGen.writeEndObject();
					} catch (IOException e) {
						e.printStackTrace();
					}	
				});
	        jGen.writeEndArray();
	    }
	    
	    jGen.writeEndObject();
	}

}
