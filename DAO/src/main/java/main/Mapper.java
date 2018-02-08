package main;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Mapper {
	static ObjectMapper getMapper(){
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    	mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    	mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    	mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    	return mapper;
	}
}
