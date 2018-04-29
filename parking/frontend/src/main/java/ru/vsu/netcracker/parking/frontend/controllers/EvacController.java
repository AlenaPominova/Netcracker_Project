package ru.vsu.netcracker.parking.frontend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.vsu.netcracker.parking.frontend.objects.Obj;
import ru.vsu.netcracker.parking.frontend.services.ObjService;

@Controller
@RequestMapping(value = "/evac")
class EvacController {

    private ObjService objService;

    @Autowired
    public EvacController(ObjService objService) {
        this.objService = objService;
    }

    @PostMapping(value = "/send-request/{parkingId}")
    @ResponseBody
    public JsonNode sendEvacRequest(@PathVariable long parkingId) {
        return objService.sendEvacRequest(parkingId);
    }

    @PatchMapping(value = "/update-status", consumes = "application/json")
    public void updateStatus(@RequestBody String json) {
        //objService.updateStatus();
    }
}
