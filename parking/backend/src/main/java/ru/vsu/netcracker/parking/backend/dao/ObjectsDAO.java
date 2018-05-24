package ru.vsu.netcracker.parking.backend.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import ru.vsu.netcracker.parking.backend.exceptions.UserAlreadyExistsException;
import ru.vsu.netcracker.parking.backend.models.Obj;

import java.util.List;
import java.util.Map;

public interface ObjectsDAO {

    public Obj getObj(long objectId);

    public Obj saveObj(Obj obj)throws UserAlreadyExistsException;

    public void delete(long objectId);

    public JsonNode getObjAsJSON(long objectId);

    public List<Obj> getAllObj(String objectType);

    public ArrayNode getAllObjAsJSON(String objectType);

    public Map<Long, String> getAttributes();

    public Obj getObjByUserName(String userName);
}