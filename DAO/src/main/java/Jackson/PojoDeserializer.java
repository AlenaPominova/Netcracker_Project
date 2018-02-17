package Jackson;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import main.Pojo;

public class PojoDeserializer extends StdDeserializer<Pojo> {
    public PojoDeserializer() {
        this(null);
    }

    public PojoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Pojo deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    	Pojo result=new Pojo();
        JsonNode node = jp.getCodec().readTree(jp);
        result.setId(node.get("id").bigIntegerValue());
        if(node.get("owner_id")!=null)
        	result.setOwnerId(node.get("owner_id").bigIntegerValue());
        result.setName(node.get("name").asText());
        result.setTypeId((int)node.get("typeId").numberValue());

        JsonNode values=node.get("values");
        if(values!=null){
	        HashMap<Long, String> valuess=new HashMap<Long,String>();
	        values.forEach(x->valuess.put(x.get("attrId").asLong(),x.get("value").asText()));
	        result.setValues(valuess);
        }
        values=node.get("listValues");
        if(values!=null){
	        HashMap<Long, Long> listValues=new HashMap<Long,Long>();
	        values.forEach(x->listValues.put(x.get("attrId").asLong(),x.get("listValueId").asLong()));
	        result.setListValues(listValues);
        }

        values=node.get("dateValues");
        if(values!=null){
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	        HashMap<Long, Timestamp> date=new HashMap<Long,Timestamp>();
	        values.forEach(x->{
				try {
					Date thisDate = (Date) df.parse(x.get("value").asText());
					long time = thisDate.getTime();
					date.put(x.get("attrId").asLong(),new Timestamp(time));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
	        result.setDate(date);
        }

        values=node.get("references");
        if(values!=null){
	        HashMap<Long, BigInteger> refs=new HashMap<Long,BigInteger>();
	        values.forEach(x->refs.put(x.get("attrId").asLong(),x.get("ref").bigIntegerValue()));
	        result.setReference(refs);
        }

        return result;
    }
}
