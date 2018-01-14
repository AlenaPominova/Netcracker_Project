package ru.vsu.netcracker.parking.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.netcracker.parking.backend.objects.User;
import ru.vsu.netcracker.parking.backend.services.ProfileService;

import java.util.Map;

@RestController
@RequestMapping(value = "/profiles")
public class ProfilesController {

    @Autowired
    private ProfileService profileService;

    @GetMapping(value = "/{profileId}", produces = "application/json")
 //   public ResponseEntity<User> getProfile(@PathVariable long profileId){
    public User getProfile(@PathVariable long profileId){
        return profileService.getProfile(profileId);
//        return new ResponseEntity<User>(profileService.getProfile(profileId), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public Map<Long, User> getAllProfiles(){
        return profileService.getAllProfiles();
    }

}
