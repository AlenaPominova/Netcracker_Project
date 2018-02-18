package Jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import main.ConcreteDAO;
import main.Pojo;

public class PojoSerializer extends JsonSerializer<Pojo> {

	@Override
	public void serialize(Pojo obj, JsonGenerator jGen, SerializerProvider arg2) throws IOException {
	    jGen.writeStartObject();
	    jGen.writeNumberField("id",obj.getId());
	    jGen.writeNumberField("typeId", obj.getTypeId());
	    jGen.writeNumberField("owner_id",obj.getOwnerId());
	    jGen.writeStringField("name", obj.getName());
	    if(obj.getDescription()!=null)
	    jGen.writeStringField("description", obj.getDescription());
	    if (obj.getValues()!=null) {
	    	jGen.writeArrayFieldStart("values");

	        	obj.getValues().forEach((k,v) -> {
					try {
						String name=ConcreteDAO.getAttrName(k);
						jGen.writeStartObject();
						jGen.writeNumberField("attrId", k);
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
	    if (obj.getDate()!=null) {
	    	jGen.writeArrayFieldStart("dateValues");
	        	obj.getDate().forEach((k,v) -> {
					try {
						String name=ConcreteDAO.getAttrName(k);
						jGen.writeStartObject();
						jGen.writeNumberField("attrId", k);
						jGen.writeStringField("name", name);
						jGen.writeStringField("value", v.toString());
						jGen.writeEndObject();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	        jGen.writeEndArray();
	    }
	    if (obj.getListValues()!=null) {
	    	jGen.writeArrayFieldStart("listValues");
	        	obj.getListValues().forEach((k,v) -> {
					try {
						String name=ConcreteDAO.getAttrName(k);
						String value=ConcreteDAO.getAttrName(k);
						jGen.writeStartObject();
						jGen.writeNumberField("attrId", k);
						jGen.writeStringField("name", name);
						jGen.writeNumberField("listValueId", v);
						jGen.writeEndObject();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	        jGen.writeEndArray();
	    }
	    if (obj.getReference()!=null) {
	    	jGen.writeArrayFieldStart("references");
	        	obj.getReference().forEach((k,v) -> {
					try {
						String attr_name=ConcreteDAO.getAttrName(k);
						String obj_name=ConcreteDAO.getObjectName(v);
						jGen.writeStartObject();
						jGen.writeNumberField("attrId", k);
						jGen.writeStringField("name", attr_name);
						jGen.writeNumberField("ref", v);
						jGen.writeStringField("objName", obj_name);
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
