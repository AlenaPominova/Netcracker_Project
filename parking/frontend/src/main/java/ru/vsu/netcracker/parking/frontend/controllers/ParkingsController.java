package ru.vsu.netcracker.parking.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.vsu.netcracker.parking.frontend.objects.Obj;
import ru.vsu.netcracker.parking.frontend.services.ObjService;
import ru.vsu.netcracker.parking.frontend.utils.CustomTimestampConverter;

import javax.jws.WebParam;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

@Controller
@RequestMapping(value = "/parkings")
public class ParkingsController {

    private ObjService objService;

    @Autowired
    public ParkingsController(ObjService objService) {
        this.objService = objService;
    }

    @GetMapping(value = "/{parkingId}")
    public String getParking(@PathVariable long parkingId, Model model) {

        Obj obj = objService.get(parkingId);
        model.addAttribute("parking", obj);
        return "parking";
    }

    @GetMapping(value = "/{parkingId}/rent")
    public String rentParking(@PathVariable long parkingId,
                              Model model,
                              @RequestParam(value = "status", required = false) String status) {
        Obj obj = objService.get(parkingId);
        model.addAttribute("parking", obj);
        if (status != null) {
            if (status.equals("confirmed")) {
                try {
                    objService.takeParking(obj);
                    model.addAttribute("success", "Аренда прошла успешно");
                } catch (IllegalArgumentException e) {
                    model.addAttribute("error", "Ошибка");
                }
            }
        }
        return "confirmation";
    }

    @GetMapping(value = "")
    public String getAllParkings(Model model) {
        Map<Long, Obj> map = objService.getAll();
        model.addAttribute("parkingsList", map);
        return "parkings";
    }

    @PostMapping(value = "")
    public String createParking(@ModelAttribute("obj") Obj parking) {
        Obj obj = objService.save(parking);
        return "redirect:/profiles/" + obj.getId();
    }

    @GetMapping(value = "/{parkingId}/edit")
    public ModelAndView getUpdateParking(@PathVariable long parkingId, ModelMap model) {
        ModelAndView editParkingPage = new ModelAndView();
        editParkingPage.addObject("parking", objService.get(parkingId));
        editParkingPage.setViewName("update-parking");
        return editParkingPage;
    }

    @PutMapping(value = "/{parkingId}/edit")
    public String updateParking(@ModelAttribute("obj") Obj parking, @ModelAttribute("currentUserId") long currentUserId) {
        Obj obj = objService.save(parking);
        return "redirect:/profiles/" + currentUserId;
    }
}
