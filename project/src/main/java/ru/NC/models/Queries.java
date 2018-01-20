package ru.NC.models;


/**
 * Class for storing sql queries in static strings
 *@author Alena Pominova
 *@version 1.0
 */
public class Queries {

    public static final String GET_PARAMS = "SELECT\t" +
            "\tA.name,\n" +
            "\tcoalesce(select_references.ref_obj, '') || coalesce(select_params.p_value, '') as value\n" +
            "  FROM  \"OBJECTS\" O\n" +
            "\tleft join \"OBJECT_TYPES\" OT \n" +
            "\t on O.object_type_id = OT.object_type_id\n" +
            "\tleft join \"ATTR_OBJECT_TYPES\" AOT \n" +
            "\t on AOT.object_type_id = OT.object_type_id\n" +
            "\tleft join \"ATTRIBUTES\" A \n" +
            "\t on A.attr_id = AOT.attr_id\n" +
            "\tleft join (\n" +
            "\t  select  distinct A.attr_id as id,\n" +
            "\t\t  R.object_id as obj_id,\n" +
            "\t\t  O.name as ref_obj\n" +
            "\t   from  \"ATTRIBUTES\" A\n" +
            "\t\t left join \"REFERENCES\" R on\n" +
            "\t\t  A.attr_id = R.attr_id\n" +
            "\t\t left join \"OBJECTS\" O on\n" +
            "\t\t  R.reference = O.object_id\n" +
            "\t   where R.attr_id is not null \n" +
            "\t) as select_references \n" +
            "\t on A.attr_id = select_references.id and O.object_id = select_references.obj_id\n" +
            "\tleft join (\n" +
            "\t  select  distinct A.attr_id as id,\n" +
            "\t\t  A.name,\n" +
            "\t\t  P.value as p_value\n" +
            "\t   from  \"ATTRIBUTES\" A\n" +
            "\t\t left join \"PARAMS\" P on\n" +
            "\t\t  A.attr_id = P.attr_id\n" +
            "\t   where P.attr_id is not null and P.object_id = ?\n" +
            "\t) as select_params \n" +
            "\t on A.attr_id = select_params.id\n" +
            "\twhere O.object_id = ?;";
    public static final String GET_PARAMETER = "SELECT\t" +
            "\tA.name,\n" +
            "\tcoalesce(select_references.ref_obj, '') || coalesce(select_params.p_value, '') as value\n" +
            "  FROM  \"OBJECTS\" O\n" +
            "\tleft join \"OBJECT_TYPES\" OT \n" +
            "\t  on O.object_type_id = OT.object_type_id\n" +
            "\tleft join \"ATTR_OBJECT_TYPES\" AOT \n" +
            "\t  on AOT.object_type_id = OT.object_type_id\n" +
            "\tleft join \"ATTRIBUTES\" A \n" +
            "\t  on A.attr_id = AOT.attr_id\n" +
            "\tleft join (\n" +
            "\t  select  distinct A.attr_id as id,\n" +
            "\t\t  R.object_id as obj_id,\n" +
            "\t\t  O.name as ref_obj\n" +
            "\t   from  \"ATTRIBUTES\" A\n" +
            "\t         left join _references R on\n" +
            "\t           A.attr_id = R.attr_id and A.attr_id = ?\n" +
            "\t         left join \"OBJECTS\" O on\n" +
            "\t\t   R.reference = O.object_id\n" +
            "\t   where R.attr_id is not null \n" +
            "\t) as select_references \n" +
            "          on A.attr_id = select_references.id and O.object_id = select_references.obj_id  \n" +
            "\tleft join (\n" +
            "\t  select  distinct A.attr_id as id,\n" +
            "\t\t  A.name,\n" +
            "\t          P.value as p_value\n" +
            "\t    from  \"ATTRIBUTES\" A\n" +
            "\t\t  left join \"PARAMS\" P on\n" +
            "\t\t    A.attr_id = P.attr_id and A.attr_id = ?\n" +
            "\t    where P.attr_id is not null and P.object_id = ? \n" +
            "\t) as select_params \n" +
            "\t  on A.attr_id = select_params.id \n" +
            "\twhere O.object_id = ? and (select_params.id is not null or select_references.id is not null);";
    public static final String GET_OBJECT_INFO = "SELECT\tO.name as name,\n" +
            "\tO.description as description,\n" +
            "\tO.parent_id as parent_id,\n" +
            "\tO.object_type_id as type_id\n" +
            "  FROM  \"OBJECTS\" O\n" +
            "\twhere O.object_id = ?;";
    public static final String GET_VALUES = "SELECT " +
            "\tselect_params.id as name,\n" +
            "\tselect_params.p_value as value \n" +
            "  FROM  \"OBJECTS\" O\n" +
            "\tleft join \"OBJECT_TYPES\" OT \n" +
            "\t on O.object_type_id = OT.object_type_id\n" +
            "\tleft join \"ATTR_OBJECT_TYPES\" AOT \n" +
            "\t on AOT.object_type_id = OT.object_type_id\n" +
            "\tleft join \"ATTRIBUTES\" A \n" +
            "\t on A.attr_id = AOT.attr_id\n" +
            "\tjoin (\n" +
            "\t  select  distinct A.attr_id as id,\n" +
            "\t\t  A.name as p_name,\n" +
            "\t\t  P.value as p_value\n" +
            "\t   from  \"ATTRIBUTES\" A\n" +
            "\t\t left join \"PARAMS\" P on\n" +
            "\t\t  A.attr_id = P.attr_id\n" +
            "\t   where P.attr_id is not null and P.object_id = ? and P.date_value is null\n" +
            "\t) as select_params \n" +
            "\t on A.attr_id = select_params.id\n" +
            "\twhere O.object_id = ?\n" +
            ";";
    public static final String GET_DATE = "SELECT " +
            "\tselect_params.id as name,\n" +
            "\tto_char(select_params.d_value, 'YYYY-MM-DD') as value\n" +
            "  FROM  \"OBJECTS\" O\n" +
            "\tleft join \"OBJECT_TYPES\" OT \n" +
            "\t on O.object_type_id = OT.object_type_id\n" +
            "\tleft join \"ATTR_OBJECT_TYPES\" AOT \n" +
            "\t on AOT.object_type_id = OT.object_type_id\n" +
            "\tleft join \"ATTRIBUTES\" A \n" +
            "\t on A.attr_id = AOT.attr_id\n" +
            "\tjoin (\n" +
            "\t  select  distinct A.attr_id as id,\n" +
            "\t\t  A.name as p_name,\n" +
            "\t\t  P.date_value as d_value\n" +
            "\t   from  \"ATTRIBUTES\" A\n" +
            "\t\t left join \"PARAMS\" P on\n" +
            "\t\t  A.attr_id = P.attr_id\n" +
            "\t   where P.attr_id is not null and P.object_id = ? and P.value is null\n" +
            "\t) as select_params \n" +
            "\t on A.attr_id = select_params.id\n" +
            "\twhere O.object_id = ?\n" +
            ";";
    public static final String GET_LIST_VALUES = "";
    public static final String GET_REFERENCE = "SELECT\tselect_references.o_id as name, \n" +
            "\tselect_references.ref_obj as value\n" +
            "  FROM  \"OBJECTS\" O\n" +
            "\tleft join \"OBJECT_TYPES\" OT \n" +
            "\t on O.object_type_id = OT.object_type_id\n" +
            "\tleft join \"ATTR_OBJECT_TYPES\" AOT \n" +
            "\t on AOT.object_type_id = OT.object_type_id\n" +
            "\tleft join \"ATTRIBUTES\" A \n" +
            "\t on A.attr_id = AOT.attr_id\n" +
            "\tjoin (\n" +
            "\t  select  distinct A.attr_id as id,\n" +
            "\t\t  R.object_id as obj_id,\n" +
            "\t\t  O.name as ref_obj,\n" +
            "\t\t  O.object_id as o_id\n" +
            "\t   from  \"ATTRIBUTES\" A\n" +
            "\t\t left join \"REFERENCES\" R on\n" +
            "\t\t  A.attr_id = R.attr_id\n" +
            "\t\t left join \"OBJECTS\" O on\n" +
            "\t\t  R.reference = O.object_id\n" +
            "\t   where R.attr_id is not null \n" +
            "\t) as select_references \n" +
            "\t on A.attr_id = select_references.id and O.object_id = select_references.obj_id\n" +
            "\twhere O.object_id = ?\n" +
            ";";

    public static final String UPDATE_OBJECT = "UPDATE \"OBJECTS\"\n"+
            "   SET name=?, description=?, object_type_id=?\n"+
            " WHERE object_id=? ;\n";
    public static final String SET_PARAMETER = "UPDATE \"PARAMS\" P\n" +
            "SET VALUE = ? WHERE P.attr_id = ? and P.object_id = ?";
    public static final String SET_REFERENCE = "UPDATE \"REFERENCE\" P\n" +
            "SET REFERENCE = ? WHERE P.attr_id = ? and P.object_id = ?";
    public static final String SET_DATE = "UPDATE \"PARAMS\" P\n" +
            "SET DATE_VALUE = ? WHERE P.attr_id = ? and P.object_id = ?";

    public static final String DELETE = "DELETE FROM \"OBJECTS\" " +
            "WHERE object_id = ?;";

    public static final String CREATE_OBJECT = "INSERT INTO \"OBJECTS\"(\n" +
            "            object_id, name, description, object_type_id)\n" +
            "    VALUES (?, ?, ?, ?);";
    public static final String INSERT_PARAMS = "INSERT INTO \"PARAMS\"(\n"+
            "            object_id, attr_id, value, date_value, list_value_id)\n"+
            "    VALUES (?, ?, ?, ?, ?);";
    public static final String INSERT_REFERENCES = "INSERT INTO \"REFERENCES\"(\n"+
            "            object_id, attr_id, reference)\n"+
            "    VALUES (?, ?, ?);";

//    SetListValue("UPDATE INTO \"PARAMS\" P" +
//            "SET DATE_VALUE = ? WHERE P.attr_id = ? and P.object_id = ?")
    ;
}
