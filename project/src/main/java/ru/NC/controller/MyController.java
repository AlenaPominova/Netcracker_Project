package ru.NC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.NC.dao.ObjectDaoImpl;
import ru.NC.models.Obj;

@Controller
public class MyController {
    @Autowired
    private ObjectDaoImpl objectDao;

    @RequestMapping(value = "/object/{id}", method = RequestMethod.GET)
    public String addStudent(@PathVariable Integer id, ModelMap model) {
        Obj obj = objectDao.read(id);
        model.addAttribute("name", obj.getName());
        model.addAttribute("typeId", obj.getTypeId());
        model.addAttribute("id", obj.getId());
        model.addAttribute("values", obj.getValues());
        model.addAttribute("date", obj.getDate());
        model.addAttribute("references", obj.getReference());

        return "result";
    }

    @RequestMapping(value = "/object", method = RequestMethod.GET)
    public String saveOrUpdateObject(@ModelAttribute("userForm")Obj obj) {
            objectDao.update(obj);
            return "redirect:object/" + obj.getId();
    }

    // show update form
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String showUpdateUserForm(@PathVariable("id") int id, Model model) {
        Obj obj = objectDao.read(id);
        model.addAttribute("userForm", obj);
        return "updateForm";
    }

}
