package ru.NC.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import ru.NC.dao.impl.ParkingDAOImpl;
import ru.NC.json.ParkingParser;
import ru.NC.models.Parking;
import ru.NC.service.IService;

import java.util.List;

public class ParkingService implements IService {
    @Autowired
    private ParkingDAOImpl parkingDAO;
    @Autowired
    private ParkingParser parkingParser;

    public void fill(ArrayNode objectList) {
        List<Parking> parkings =  parkingParser.parseAll(objectList);
        for (Parking parking: parkings) {
            parkingDAO.save(parking);
        }
    }
    public Parking getById(long id){
        return parkingDAO.findById(id);
    }
}
