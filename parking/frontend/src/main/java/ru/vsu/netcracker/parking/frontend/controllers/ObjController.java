package ru.vsu.netcracker.parking.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.vsu.netcracker.parking.frontend.exceptions.ResourceNotFoundException;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            try {
                long currentUserId = objService.getObjByUsername(auth.getPrincipal().toString()).getId();
                model.addAttribute("currentUserId", currentUserId);
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "parkings";
    }

    @GetMapping(value = "/profiles/{objectId}")
    public String profile(@PathVariable long objectId, Model model) {
        model.addAttribute("user", objService.get(objectId));
        model.addAttribute("ownedParkings", objService.getAllParkingsOwnedByUser(objectId));
        return "profile";
    }

//    @GetMapping(value = "/profiles/{objectId}")
//    @ResponseBody
//    public Obj profile(@PathVariable long objectId, Model model) {
//        return objService.get(objectId);
//    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute("obj") Obj obj) {
        try {
            objService.registerNewUser(obj);
        } catch (UserAlreadyExistsException e) {
            return "redirect:/login?already-exists";
        }
        return "redirect:/login?reg";
    }

    @PutMapping(value = "/profiles/{objectId}")
    public String updateUser(@PathVariable long objectId,
                             @ModelAttribute("obj") Obj obj) {
        objService.save(obj);
        return "redirect:/profiles/" + objectId;
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
            modelAndView.addObject("reg-error", "User with such email or phone already exists.");
        }
        modelAndView.setViewName("login");

        return modelAndView;
    }
}
