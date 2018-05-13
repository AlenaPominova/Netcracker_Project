package ru.vsu.netcracker.parking.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.vsu.netcracker.parking.frontend.exceptions.UserAlreadyExistsException;
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

    @GetMapping(value = "/profile")
    public String profile(Model model) {
        Map<Long, Obj> map = objService.getAll();
        model.addAttribute("parkingsList", map);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        //model.addAttribute("user", objService.getObjByUsername())
        return "profile";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute("obj") Obj obj) {
        try {
            objService.registerNewUser(obj);
        } catch (UserAlreadyExistsException e) {
            return "redirect:/login?already-exists";
        }
        return "redirect:/login?reg";
    }

    @GetMapping(value = "/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout,
                              @RequestParam(value = "reg", required = false) String reg,
                              @RequestParam(value = "already-exists", required = false) String alreadyExists) {
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
        if (alreadyExists != null) {
            modelAndView.addObject("regerror", "User with such email or phone already exists.");
        }
        modelAndView.setViewName("login");

        return modelAndView;
    }
}
