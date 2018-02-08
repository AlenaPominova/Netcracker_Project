package main;

import java.io.File;
import java.io.IOException;

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

	   public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
	      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	      ConcreteDAO myDAO =
	         (ConcreteDAO)context.getBean("DAO");
	      OBJECT_MAPPER.writeValue(new File("custresult.json"),myDAO.read(4));
	   }
}


