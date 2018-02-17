package main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;

import Jackson.CustomObjectMapper;

/**
 * @author Incurable
 *
 */
public class Main {
	 private static final CustomObjectMapper OBJECT_MAPPER =
	          new CustomObjectMapper();

	   public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException, NoSuchFieldException, SecurityException {
	      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	      ConcreteDAO myDAO =
	         (ConcreteDAO)context.getBean("DAO");
	      OBJECT_MAPPER.writeValue(new File("custresult.json"),myDAO.read(new BigInteger("1802177257163")));


//	      Pojo p = OBJECT_MAPPER.readValue(new File("custresult.json"), Pojo.class);
//	      System.out.println(p.toString());
	   }
}


