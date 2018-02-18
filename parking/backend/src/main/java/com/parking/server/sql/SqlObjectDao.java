package com.parking.server.sql;


public class SqlObjectDao {

    public String getSelectQuery() {
        return "select  o.object_id,\n" +
                "        o.name,\n" +
                "        o.object_type_id\n" +
                "from \"OBJECTS\" o;";
    }

    public String getCreateQuery() {
        return "insert into \"OBJECTS\"(\n" +
                "  object_id, object_type_id, name)\n" +
                "values ";//  (?, ?, ?);";
    }

    public String getInsertQuery() {
        return "insert into \"PARAMS\"(\n" +
                "  attr_id, object_id, value, date_value, list_value_id)\n" +
                "values ";// (?, ?, ?, ?, ?);";
    }

    public String getCreateReferencesQuery() {
        return "insert into \"REFERENCES\"(\n" +
                "  attr_id, reference, object_id)\n" +
                "values ";//(?, ?, ?);";
    }

    public String getUpdateValuesQuery()
    {
        return "with UPD as (\n" +
                "      update \"PARAMS\" set value=? \n" +
                "      where object_id = ? and attr_id=?\n" +
                "      returning object_id\n" +
                "),\n" +
                "INS as (\n" +
                "      select ?,?,?\n" +
                "      where not exists (\n" +
                "          select * from UPD)\n" +
                "  )\n" +
                "insert into \"PARAMS\"(object_id, value, attr_id)\n" +
                "      select * from INS;";
    }

    public String getUpdateDatesQuery()
    {
        return "with UPD as (\n" +
                "      update \"PARAMS\" set date_value=? \n" +
                "      where object_id = ? and attr_id=?\n" +
                "      returning object_id\n" +
                "),\n" +
                "INS as (\n" +
                "      select ?,?,?\n" +
                "      where not exists (\n" +
                "          select * from UPD)\n" +
                "  )\n" +
                "insert into \"PARAMS\"(object_id, value, attr_id)\n" +
                "      select * from INS;";
    }

    public String getUpdateList_values_idQuery()
    {
        return "with UPD as (\n" +
                "      update \"PARAMS\" set list_value_id=? \n" +
                "      where object_id = ? and attr_id=?\n" +
                "      returning object_id\n" +
                "),\n" +
                "INS as (\n" +
                "      select ?,?,?\n" +
                "      where not exists (\n" +
                "          select * from UPD)\n" +
                "  )\n" +
                "insert into \"PARAMS\"(object_id, value, attr_id)\n" +
                "      select * from INS;";
    }

    public String getAttrTypeQuery(){
        return "select  a.attr_type_id,\n" +
                "       a_t.name\n" +
                "from \"ATTRIBUTES\" a\n" +
                "  left join \"ATTR_TYPES\" a_t on a_t.attr_type_id = a.attr_type_id\n" +
                "where attr_id = ?;";
    }

    public String getDeleteQuery() {
        return "delete  from \"OBJECTS\"\n" +
                "       where object_id = ?;";
    }

    public String getFindByPKQuery() {
        return "select  o.object_id,\n" +
                "        o.name,\n" +
                "        o.object_type_id,\n" +
                "        o.parent_id,\n" +
                "        p.attr_id,\n" +
                "        p.value,\n" +
                "        p.date_value,\n" +
                "        p.list_value_id,\n" +
                "        r.reference\n" +
                "from \"OBJECTS\" o\n" +
                "        left join \"PARAMS\" p on p.object_id = o.object_id\n" +
                "        left join \"REFERENCES\" r on r.object_id = o.object_id\n" +
                "where o.object_id = ?;";
    }

    public String getNameParamsQuery() {
        return "select\t  o.object_id,\n" +
                "\t  o.name,\n" +
                "\t  o.object_type_id,\n" +
                "\t  o.parent_id,\n" +
                "\t  (select a.name from \"ATTRIBUTES\" a where a.attr_id = p.attr_id),\n" +
                "\t  p.attr_id,\n" +
                "\t  p.value,\n" +
                "\t  p.date_value,\n" +
                "\t  p.list_value_id,\n" +
                "\t  r.reference,\n" +
                "\t (select o2.name from \"OBJECTS\" o2 where o2.object_id = r.reference)\n" +
                "from \"OBJECTS\" o\n" +
                "\t  left join \"PARAMS\" p on p.object_id = o.object_id\n" +
                "\t  left join \"REFERENCES\" r on r.object_id = o.object_id;";
    }
}
