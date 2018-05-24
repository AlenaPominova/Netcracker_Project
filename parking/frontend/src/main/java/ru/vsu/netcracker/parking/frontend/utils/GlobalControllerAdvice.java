package ru.vsu.netcracker.parking.frontend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.vsu.netcracker.parking.frontend.exceptions.ResourceNotFoundException;
import ru.vsu.netcracker.parking.frontend.services.ObjService;

import java.sql.Timestamp;

@ControllerAdvice
public class GlobalControllerAdvice {

    private ObjService objService;
//    private static final String MAINPAGE = "http://frontend-parkingo.37.46.133.173.nip.io";
    private static final String MAINPAGE = "http://localhost:8082";

    @Autowired
    public GlobalControllerAdvice(ObjService objService) {
        this.objService = objService;
    }

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.registerCustomEditor(Timestamp.class, new CustomTimestampConverter("HH:mm"));
    }

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("mainPageUrl", MAINPAGE);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            try {
                long currentUserId = objService.getObjByUsername(auth.getPrincipal().toString()).getId();
                model.addAttribute("currentUserId", currentUserId);
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
