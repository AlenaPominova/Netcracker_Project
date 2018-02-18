package com.site.sql;

public class SQLConstant {

    public static String GET_INFO_BY_ID = "SELECT  *\n" +
            "\n" +
            "  FROM  \"OBJECTS\" o\n" +
            "  WHERE o.object_id = ?;";
    public static String GET_VALUES_BY_ID = "SELECT  p.attr_id,\n" +
            "        p.value,\n" +
            "        p.list_value_id,\n" +
            "        p.date_value,\n" +
            "        a.name\n" +
            "  FROM  \"ATTR_OBJECT_TYPES\" aot\n" +
            "        JOIN  \"PARAMS\" p ON p.attr_id = aot.attr_id AND p.object_id = ?\n" +
            "        JOIN \"ATTRIBUTES\" a ON p.attr_id = a.attr_id\n" +
            "  WHERE aot.object_type_id = ?;";
    public static String DELETE_BY_ID = "\"DELETE FROM \\\"OBJECTS\\\" WHERE object_id = ?;\"";
}
