package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	   public static void main(String[] args) {
	      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	      ConcreteDAO myDAO =
	         (ConcreteDAO)context.getBean("DAO");
	      Pojo p=myDAO.read(4);
	      Pojo o=myDAO.read(5);
	      p.setValues(o.getValues());
	      myDAO.update(p);
	   }
}


