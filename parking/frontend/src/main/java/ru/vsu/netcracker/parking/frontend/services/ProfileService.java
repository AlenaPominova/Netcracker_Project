package ru.vsu.netcracker.parking.frontend.services;

import org.springframework.stereotype.Service;
import ru.vsu.netcracker.parking.frontend.objects.User;

import java.util.Map;

@Service
public class ProfileService {

//    private ObjectsDAO dao;
//
//    @Autowired
//    public ProfileService(ObjectsDAO objectsDAO){
//        this.dao = objectsDAO;
//    }

    public User getProfile(long profileId){
        User user = new User();
//        Map<Long, String> attrValues= dao.getAllParams(profileId);
//        user.setRole(attrValues.get(new Long(200)));
//        user.setName(attrValues.get(new Long(201)));
//        user.setPhone(attrValues.get(new Long(202)));
//        user.setEmail(attrValues.get(new Long(203)));
//        user.setPassword(attrValues.get(new Long(204)));
//        user.setAddress(attrValues.get(new Long(205)));
//        String str = attrValues.get(new Long(206));
//        String substr = str.substring(0,19);
//        Timestamp test = Timestamp.valueOf(substr);
//
//
//        LocalDate birthDate = LocalDate.parse(substr.substring(0,10));
//        user.setBirthDate(birthDate);
        return user;
    }



    public Map<Long, Map<Long, String>> getAllProfiles(){
        return null;//dao.getAllByObjectType(2);
    }
}
