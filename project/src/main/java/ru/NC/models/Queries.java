package ru.NC.models;


/**
 * Class for storing sql queries in static strings
 *@author Alena Pominova
 *@version 1.0
 */
public class Queries {

    public static final String GET_OBJECT_INFO = "SELECT\tO.name as name,\n" +
            "\tO.description as description,\n" +
            "\tO.parent_id as parent_id,\n" +
            "\tO.object_type_id as type_id\n" +
            "  FROM  \"OBJECTS\" O\n" +
            "\twhere O.object_id = ?;";
    public static final String GET_VALUES = "SELECT\tA.attr_id as name,\n" +
            "\tP.value as value \n" +
            "  FROM  \"OBJECT_TYPES\" OT \n" +
            "\tjoin \"ATTR_OBJECT_TYPES\" AOT \n" +
            "\t on AOT.object_type_id = OT.object_type_id\n" +
            "\tjoin \"ATTRIBUTES\" A \n" +
            "\t on A.attr_id = AOT.attr_id\n" +
            "\tjoin \"PARAMS\" P \n" +
            "\t on A.attr_id = P.attr_id\n" +
            "\twhere OT.object_type_id = ? and P.object_id = ? and P.date_value is null\n" +
            ";";
    public static final String GET_DATE = "SELECT\tA.attr_id as name,\n" +
            "\tto_char(P.date_value, 'yyyy-MM-dd HH:mm:ss') as value\n" +
            "  FROM  \"OBJECT_TYPES\" OT \n" +
            "\tjoin \"ATTR_OBJECT_TYPES\" AOT \n" +
            "\t on AOT.object_type_id = OT.object_type_id\n" +
            "\tjoin \"ATTRIBUTES\" A \n" +
            "\t on A.attr_id = AOT.attr_id\n" +
            "\tjoin \"PARAMS\" P \n" +
            "\t on A.attr_id = P.attr_id\n" +
            "\twhere OT.object_type_id = ? and P.object_id = ? and P.value is null\n" +
            ";";
    public static final String GET_LIST_VALUES = "";
    public static final String GET_REFERENCE = "SELECT\tA.attr_id as name,\n" +
            "\tR.reference as value\n" +
            "  FROM  \"OBJECT_TYPES\" OT \n" +
            "\tjoin \"ATTR_OBJECT_TYPES\" AOT \n" +
            "\t on AOT.object_type_id = OT.object_type_id\n" +
            "\tjoin \"ATTRIBUTES\" A \n" +
            "\t on A.attr_id = AOT.attr_id\n" +
            "\tjoin \"REFERENCES\" R\n" +
            "\t on A.attr_id = R.attr_id\n" +
            "\twhere OT.object_type_id = ? and R.object_id = ? \n" +
            ";";
    public static final String SET_PARAMETER = "INSERT INTO \"PARAMS\" (attr_id, object_id, value)" +
            " values (?, ?, ?) ON CONFLICT (attr_id, object_id) \n" +
            "DO\n" +
            " UPDATE\n" +
            "   SET value = EXCLUDED.value;";
    public static final String SET_REFERENCE = "INSERT INTO \"REFERENCES\" (attr_id, object_id, reference)" +
            " values (?, ?, ?) ON CONFLICT (attr_id, object_id) \n" +
            "DO\n" +
            " UPDATE\n" +
            "   SET reference = EXCLUDED.reference;";
    public static final String SET_DATE = "INSERT INTO \"PARAMS\" (attr_id, object_id, date_value)" +
            " values (?, ?, date(?)) ON CONFLICT (attr_id, object_id) \n" +
            "DO\n" +
            " UPDATE\n" +
            "   SET date_value = EXCLUDED.date_value;";

    public static final String DELETE = "DELETE FROM \"OBJECTS\" " +
            "WHERE object_id = ?;";

    public static final String CREATE_OBJECT = "INSERT INTO \"OBJECTS\"(\n" +
            "            name, description, object_type_id)\n" +
            "    VALUES (?, ?, ?);";
    public static final String INSERT_PARAMS = "INSERT INTO \"PARAMS\"(\n"+
            "            object_id, attr_id, value, date_value, list_value_id)\n"+
            "    VALUES (?, ?, ?, ?, ?);";
    public static final String INSERT_REFERENCES = "INSERT INTO \"REFERENCES\"(\n"+
            "            object_id, attr_id, reference)\n"+
            "    VALUES (?, ?, ?);";

    public static final String GET_VALUES_JSON = "SELECT\tA.attr_id as id,\n" +
            "\tA.name as name,\n" +
            "\tP.value as value \n" +
            "  FROM  \"OBJECTS\" O\n" +
            "\tjoin \"OBJECT_TYPES\" OT \n" +
            "\t on O.object_type_id = OT.object_type_id\n" +
            "\tjoin \"ATTR_OBJECT_TYPES\" AOT \n" +
            "\t on AOT.object_type_id = OT.object_type_id\n" +
            "\tjoin \"ATTRIBUTES\" A \n" +
            "\t on A.attr_id = AOT.attr_id\n" +
            "\tjoin \"PARAMS\" P \n" +
            "\t on A.attr_id = P.attr_id\n" +
            "\twhere O.object_id = ? and O.object_id = P.object_id and P.date_value is null\n" +
            ";";
    public static final String GET_DATE_JSON = "SELECT\tA.attr_id as id,\n" +
            "\tA.name as name,\n" +
            "\tto_char(P.date_value, 'yyyy-MM-dd HH:mm:ss') as value\n" +
            "  FROM  \"OBJECTS\" O\n" +
            "\tjoin \"OBJECT_TYPES\" OT \n" +
            "\t on O.object_type_id = OT.object_type_id \n" +
            "\tjoin \"ATTR_OBJECT_TYPES\" AOT \n" +
            "\t on AOT.object_type_id = OT.object_type_id\n" +
            "\tjoin \"ATTRIBUTES\" A \n" +
            "\t on A.attr_id = AOT.attr_id\n" +
            "\tjoin \"PARAMS\" P \n" +
            "\t on A.attr_id = P.attr_id\n" +
            "\twhere O.object_id = ? and O.object_id = P.object_id and P.value is null\n" +
            ";";
    public static final String GET_REFERENCE_JSON = "SELECT\tA.attr_id as id,\n" +
            "\tA.name as name,\n" +
            "\tR.reference as ref_id,\n" +
            "\tO.name as value\n" +
            "  FROM  \"OBJECTS\" O\n" +
            "\tjoin \"OBJECT_TYPES\" OT \n" +
            "\t on O.object_type_id = OT.object_type_id \n" +
            "\tjoin \"ATTR_OBJECT_TYPES\" AOT \n" +
            "\t on AOT.object_type_id = OT.object_type_id\n" +
            "\tjoin \"ATTRIBUTES\" A \n" +
            "\t on A.attr_id = AOT.attr_id\n" +
            "\tjoin \"REFERENCES\" R\n" +
            "\t on A.attr_id = R.attr_id\n" +
            "\tjoin \"OBJECTS\" OB\n" +
            "\t on R.reference = OB.object_id\n" +
            "\twhere O.object_id = ? and R.object_id = O.object_id\n" +
            ";";

//    SetListValue("UPDATE INTO \"PARAMS\" P" +
//            "SET DATE_VALUE = ? WHERE P.attr_id = ? and P.object_id = ?")
    ;
}
