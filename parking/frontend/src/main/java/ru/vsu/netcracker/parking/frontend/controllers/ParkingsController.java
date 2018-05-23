package ru.vsu.netcracker.parking.frontend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import java.util.Collection;
import java.util.HashMap;
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

    @PostMapping(value = "/{parkingId}/edit")
    public String updateParking(@ModelAttribute("obj") Obj parking, @PathVariable long parkingId, @ModelAttribute("currentUserId") long currentUserId) {
        Obj park = objService.get(parkingId);
        Map<Long, String> values = park.getValues();
        Map<Long, Timestamp> dateValues = park.getDateValues();
        parking.getDateValues().forEach((k, v) -> {
            dateValues.put(k, v);
        });
        parking.getValues().forEach((k, v) -> {
            values.put(k, v);
        });
        objService.save(park);
        return "redirect:/profiles/" + currentUserId;
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

    /* evacuation service */

    @GetMapping(value = "/{parkingId}/evac")
    public String evacParking(@PathVariable long parkingId,
                              @ModelAttribute("currentUserId") long currentUserId) {
        Obj obj = objService.evacParking(parkingId);
        return "redirect:/profiles/" + currentUserId;
    }


//    @RequestMapping(value = "/{parkingId}/edit", method = RequestMethod.POST, headers = {"Content-type=application/json"})
//    @ResponseBody
//    public void updateParking(@RequestBody JsonNode node, @PathVariable long parkingId){
//        JsonNode a = node;
//    }
}
