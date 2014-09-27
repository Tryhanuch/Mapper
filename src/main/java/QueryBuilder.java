import anot.Column;
import anot.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tish on 24.09.2014.
 */
public class QueryBuilder {

    public static String createTableQuerySql(Object o){
        String tableName = o.getClass().getAnnotation(Entity.class).name().toLowerCase();

        List<String> columnNames = new ArrayList<>();
        Field[] fields = o.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].getName().equals("id")) {
                String fieldName = fields[i].getAnnotation(Column.class).name().toLowerCase();
                columnNames.add(fieldName);
            }
        }

//    CREATE TABLE `my_schema`.`student` (
//    `student_id` INT NOT NULL AUTO_INCREMENT,
//    `first_name` VARCHAR(45) NULL,
//    `last_name` VARCHAR(45) NULL,
//    `age` INT NULL,
//    `group` VARCHAR(45) NULL,
//    PRIMARY KEY (`student_id`));

        String query = "";

        return query;
    }

    public static String insertQuerySQL(Object o){
        Field[] fields = o.getClass().getDeclaredFields();
        List<String> columnNames = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].getName().equals("id")) {
                String fieldName = fields[i].getAnnotation(Column.class).name().toLowerCase();
                columnNames.add(fieldName);
            }
        }

        String params = "";
        String values = "";
        for (int i = 0; i < columnNames.size(); i++) {
            if (i != (columnNames.size() - 1)){
                params += columnNames.get(i) + ", ";
                values += "?, ";
            }
            else {
                params += columnNames.get(i);
                values += "?";
            }
        }

        String tableName = o.getClass().getAnnotation(Entity.class).name().toLowerCase();

        String query = "INSERT INTO " + tableName + " (" + params + ") VALUES (" + values + ")";

        return query;
    }


}
