package ru.vsu.netcracker.parking.backend.dao;

public class PostgresSQLQueries {

    public static final String SELECT_ATTR_VALUE_QUERY =
            "SELECT \to.object_id,\n" +
            "\tCONCAT (p.value, p.date_value, referenced_objects.referenced_object_name, lv.value) as value\n" +
            "\tFROM \"OBJECTS\" o\n" +
            "\t  JOIN \"OBJECT_TYPES\" OT USING (object_type_id)\n" +
            "\t  JOIN \"ATTR_OBJECT_TYPES\" aot ON o.object_type_id = aot.object_type_id\n" +
            "\t  JOIN \"ATTRIBUTES\" a ON a.attr_id = aot.attr_id\n" +
            "\t  JOIN \"ATTR_TYPES\" AT USING (attr_type_id)\n" +
            "\t  LEFT JOIN \"REFERENCES\" ref ON o.object_id = ref.object_id AND a.attr_id = ref.attr_id\n" +
            "\t  LEFT JOIN (\n" +
            "\t\tSELECT DISTINCT ref1.reference, obj.name as referenced_object_name\n" +
            "\t\t\tFROM \"REFERENCES\" ref1\n" +
            "\t\t\tJOIN \"OBJECTS\" obj ON ref1.reference = obj.object_id\n" +
            "\t\t)  as referenced_objects\n" +
            "\t\tON ref.reference = referenced_objects.reference\n" +
            "\t LEFT JOIN \"PARAMS\" p ON a.attr_id = p.attr_id AND o.object_id = p.object_id\n" +
            "\tLEFT JOIN \"LIST_VALUES\" lv USING (list_value_id)\n" +
            "WHERE o.object_id = ? and a.attr_id = ?";

    public static final String SELECT_ALL_PARAMS_BY_OBJECT_ID_QUERY =
            "SELECT o.object_id, " +
            "ot.name as obj_type_name, " +
            "a.attr_id, " +
            "a.name as attr_name, " +
            "a.attr_type_id, " +
            "at.name as attr_type_name, " +
            "CONCAT (p.value, p.date_value, referenced_objects.referenced_object_name, lv.value) as value, " +
            "lv.list_value_id, " +
            "lv.value as list_value " +
            "FROM \"OBJECTS\" o " +
            "JOIN \"OBJECT_TYPES\" OT USING (object_type_id) " +
            "JOIN \"ATTR_OBJECT_TYPES\" aot ON o.object_type_id = aot.object_type_id " +
            "JOIN \"ATTRIBUTES\" a ON a.attr_id = aot.attr_id " +
            "JOIN \"ATTR_TYPES\" AT USING (attr_type_id) " +
            "LEFT JOIN \"REFERENCES\" ref ON o.object_id = ref.object_id AND a.attr_id = ref.attr_id " +
            "LEFT JOIN ( " +
            "SELECT DISTINCT ref1.reference, obj.name as referenced_object_name " +
            "FROM \"REFERENCES\" ref1 " +
            "JOIN \"OBJECTS\" obj ON ref1.reference = obj.object_id " +
            ") as referenced_objects " +
            "ON ref.reference = referenced_objects.reference " +
            "LEFT JOIN \"PARAMS\" p ON a.attr_id = p.attr_id AND o.object_id = p.object_id " +
            "LEFT JOIN \"LIST_VALUES\" lv USING (list_value_id) " +
            "WHERE o.object_id = ? " +
            "ORDER BY o.object_id, a.attr_id ";

    public static final String SELECT_ALL_BY_OBJECT_TYPE_QUERY =
            "SELECT \to.object_id,\n" +
            "\to.name,\n" +
            "\to.parent_id,\n" +
            "\to.object_type_id,\n" +
            "\tot.name as obj_type_name,\n" +
            "\ta.attr_id,\n" +
            "\ta.name as attr_name,\n" +
            "\ta.attr_type_id,\n" +
            "\tat.name as attr_type_name,\n" +
            "\tref.reference,\n" +
            "\treferenced_objects.referenced_object_name,\n" +
            "\tCONCAT (p.value, p.date_value, referenced_objects.referenced_object_name, lv.value) as value,\n" +
            "\tlv.list_value_id, \n" +
            "\tlv.value as list_value\n" +
            "\tFROM \"OBJECTS\" o\n" +
            "\t  JOIN \"OBJECT_TYPES\" OT USING (object_type_id)\n" +
            "\t  JOIN \"ATTR_OBJECT_TYPES\" aot ON o.object_type_id = aot.object_type_id\n" +
            "\t  JOIN \"ATTRIBUTES\" a ON a.attr_id = aot.attr_id\n" +
            "\t  JOIN \"ATTR_TYPES\" AT USING (attr_type_id)\n" +
            "\t  LEFT JOIN \"REFERENCES\" ref ON o.object_id = ref.object_id AND a.attr_id = ref.attr_id\n" +
            "\t  LEFT JOIN (\n" +
            "\t\tSELECT DISTINCT ref1.reference, obj.name as referenced_object_name\n" +
            "\t\t\tFROM \"REFERENCES\" ref1\n" +
            "\t\t\tJOIN \"OBJECTS\" obj ON ref1.reference = obj.object_id\n" +
            "\t\t)  as referenced_objects\n" +
            "\t\tON ref.reference = referenced_objects.reference\n" +
            "\t LEFT JOIN \"PARAMS\" p ON a.attr_id = p.attr_id AND o.object_id = p.object_id\n" +
            "\tLEFT JOIN \"LIST_VALUES\" lv USING (list_value_id)\n" +
            "WHERE o.object_type_id = ?\n" +
            "ORDER BY o.object_id, a.attr_id\n";
}
