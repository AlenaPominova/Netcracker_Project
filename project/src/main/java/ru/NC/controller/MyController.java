package ru.NC.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.NC.dao.ObjectDaoImpl;

@Controller
public class MyController {

    @RequestMapping(value = "/object", method = RequestMethod.GET)
    public String greeting(@RequestParam(value="id", required=false, defaultValue="1") Long id, Model model) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ObjectDaoImpl objectDao = (ObjectDaoImpl) context.getBean("objectDao");
        model.addAttribute("object", objectDao.getObjectAsJson(id));
        return "hello";
    }
}
