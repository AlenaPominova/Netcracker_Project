package ru.vsu.netcracker.parking.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.vsu.netcracker.parking.frontend.objects.User;
import ru.vsu.netcracker.parking.frontend.services.ProfileService;

import java.util.Map;

@RestController
@RequestMapping(value = "/profiles")
    public class ProfilesController {

    @Autowired
    private ProfileService profileService;

    @GetMapping(value = "/{profileId}", produces = "application/json")
    @ResponseBody
    public User getProfile(@PathVariable long profileId){

        String uri = "http://localhost:8080/backend/profiles/" + profileId;
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(uri, User.class);
        return user;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<Long, Map<Long, String>> getAllProfiles(){
        return null;
    }

}
