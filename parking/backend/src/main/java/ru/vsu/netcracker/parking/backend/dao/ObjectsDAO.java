package ru.vsu.netcracker.parking.backend.dao;


import com.fasterxml.jackson.databind.JsonNode;
import ru.vsu.netcracker.parking.backend.models.Obj;

public interface ObjectsDAO {

    public String createObj(Obj obj);
    public void updateObj(Obj obj);
    public void delete(String objId);
    public Obj getObj(String objId);
    public JsonNode getObjAsJSON(String objId);
}
