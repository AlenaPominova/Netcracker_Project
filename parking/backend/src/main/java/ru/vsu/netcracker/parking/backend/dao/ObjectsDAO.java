package ru.vsu.netcracker.parking.backend.dao;


import ru.vsu.netcracker.parking.backend.objects.User;

import java.util.Map;

public interface ObjectsDAO {
    public void delete(int objId);
    public String getParam(int objectId, int attributeId);
    public Map<Long,String> getAllParams(long objId);
    public void setParam(int attributeId, String value);
    public Map<Long, Map<Long, String>> getAllByObjectType(long objectTypeId);
}
