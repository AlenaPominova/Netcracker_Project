package ru.vsu.netcracker.parking.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vsu.netcracker.parking.frontend.objects.Obj;
import ru.vsu.netcracker.parking.frontend.services.ObjService;

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

        return "parking/parking";
    }

    @GetMapping(value = "")
    public String getAllParkings(Model model) {
        Map<Long, Obj> map = objService.getAll();
        model.addAttribute("parkingsList", map);

        return "parking/parkings";
    }

    @PutMapping(value = "/{parkingId}")
    public String takeParking(@PathVariable long parkingId,
                              @ModelAttribute("obj") Obj parking,
                              @RequestParam(value = "take", required = false) String take) {
        if (take != null) {
            objService.takeParking(parking);
        } else {
            objService.save(parking);
        }
        return "redirect:/login?reg";
    }


}
