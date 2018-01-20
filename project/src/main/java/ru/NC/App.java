package ru.NC;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.NC.dao.ObjectDaoImpl;
import ru.NC.jackson.JsonCreating;
import ru.NC.models.Obj;

import java.io.IOException;
import java.util.Map;

public class App {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ObjectDaoImpl objectDao = (ObjectDaoImpl) context.getBean("objectDao");

        System.out.println("Begin extract data--------------");

        Obj object = objectDao.read(5);

        // create a json factory to write the treenode as json. for the example
        // we just write to console
        JsonFactory jsonFactory = new JsonFactory();
        try {
            JsonGenerator generator = jsonFactory.createGenerator(System.out);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeTree(generator, JsonCreating.createJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
