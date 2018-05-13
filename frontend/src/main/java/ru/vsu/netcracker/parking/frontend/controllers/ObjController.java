package ru.vsu.netcracker.parking.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.vsu.netcracker.parking.frontend.objects.Obj;
import ru.vsu.netcracker.parking.frontend.services.ObjService;

import java.util.Map;

@Controller
public class ObjController {

    ObjService objService;

    @Autowired
    public ObjController(ObjService objService) {
        this.objService = objService;
    }

    @GetMapping(value = "")
    public String main(Model model) {
        Map<Long, Obj> map = objService.getAll();
        model.addAttribute("parkingsList", map);

        return "parkings";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute("obj") Obj obj) {
        objService.registerNewUser(obj);
        return "redirect:/login?reg";
    }

    @GetMapping(value = "/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout,
                              @RequestParam(value = "reg", required = false) String reg) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("obj", new Obj());
        if (error != null) {
            modelAndView.addObject("error", "Wrong password. Try again or click Forgot password to reset it.");
        }
        if (logout != null) {
            modelAndView.addObject("message", "You've been logged out successfully.");
        }
        if (reg != null) {
            modelAndView.addObject("message", "Registration complete. You can login now.");
        }
        modelAndView.setViewName("login");

        return modelAndView;
    }
}
