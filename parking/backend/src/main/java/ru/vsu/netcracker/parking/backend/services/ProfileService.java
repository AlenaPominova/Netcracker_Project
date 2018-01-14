package ru.vsu.netcracker.parking.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.netcracker.parking.backend.dao.ObjectsDAO;
import ru.vsu.netcracker.parking.backend.objects.User;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProfileService {

    private ObjectsDAO dao;

    @Autowired
    public ProfileService(ObjectsDAO objectsDAO){
        this.dao = objectsDAO;
    }

    public User getProfile(long profileId){
        Map<Long, String> attrValues= dao.getAllParams(profileId);
        User user = new User();
        user.setId(profileId);
        user.setRole(attrValues.get(new Long(200)));
        user.setName(attrValues.get(new Long(201)));
        user.setPhone(attrValues.get(new Long(202)));
        user.setEmail(attrValues.get(new Long(203)));
        user.setPassword(attrValues.get(new Long(204)));
        user.setAddress(attrValues.get(new Long(205)));
        String str = attrValues.get(new Long(206));
        String substr = str.substring(0,19);
        Timestamp test = Timestamp.valueOf(substr);

        LocalDate birthDate = LocalDate.parse(substr.substring(0,10));
        user.setBirthDate(birthDate);
        return user;
    }



    public Map<Long, User> getAllProfiles(){
        Map<Long, User> result = new HashMap<>();
        Map<Long, Map<Long, String>> profiles = dao.getAllByObjectType(2);
        profiles.forEach((objectId, attValues) -> {
            User user = new User();
            user.setId(objectId);
            user.setRole(attValues.get(new Long(200)));
            user.setName(attValues.get(new Long(201)));
            user.setPhone(attValues.get(new Long(202)));
            user.setEmail(attValues.get(new Long(203)));
            user.setPassword(attValues.get(new Long(204)));
            user.setAddress(attValues.get(new Long(205)));
            String str = attValues.get(new Long(206));
            String substr = str.substring(0,19);
            Timestamp test = Timestamp.valueOf(substr);

            LocalDate birthDate = LocalDate.parse(substr.substring(0,10));
            user.setBirthDate(birthDate);
            result.put(objectId, user);
        });

        return result;
    }
}
