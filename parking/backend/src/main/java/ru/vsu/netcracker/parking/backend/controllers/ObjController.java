package ru.vsu.netcracker.parking.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.vsu.netcracker.parking.backend.models.Obj;
import ru.vsu.netcracker.parking.backend.services.ObjService;

@Controller
@RequestMapping(value = "/management")
public class ObjController {

    private ObjService objService;

    @Autowired
    public ObjController(ObjService objService) {
        this.objService = objService;
    }

    @GetMapping(value = "/login")
    public String reg() {
        return "login";
    }

    @GetMapping("/{objectId}")
    public ModelAndView getObj(@PathVariable long objectId) {
        Obj obj = objService.getObj(objectId);
        return new ModelAndView("objInfo", "obj", obj);
    }

    @PostMapping("")
    public String saveOrDeleteObj(@ModelAttribute("obj") Obj obj, @RequestParam("button") String submitValue) {
        if (submitValue.equalsIgnoreCase("Save")) {
            objService.saveObj(obj);
            return "redirect:management/" + obj.getId();
        } else if (submitValue.equalsIgnoreCase("Delete")) {
            objService.deleteObj(obj.getId());
            return "Object_deleted";
        }
        return "error";
    }

//    @GetMapping(value = "/test")    //for testing imitate response from evac service with updated status "Completed"
//    public void test() {
//        objService.updateEvacStatus(11L);
//    }
}
