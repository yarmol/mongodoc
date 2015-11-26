package me.jarad.mongo.dao.gears;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by vitaly on 04.11.2015.
 */
public class FieldPatternMapper {
    static Map<String,String> fieldMap = new TreeMap<>();
    static {
        fieldMap.put("user","^([A-Za-z_]){3,}$");
        fieldMap.put("password","^.{6,}$");
        fieldMap.put("email","[A-Za-z_.]+@[A-Za-z_.]+");
    }
    public static Map<String,String> getMapper() {
        return fieldMap;
    }

}
