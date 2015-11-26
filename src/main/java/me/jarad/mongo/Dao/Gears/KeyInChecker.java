package me.jarad.mongo.dao.gears;

import java.util.Map;

/**
 * Created by vitaly on 04.11.2015.
 */
public class KeyInChecker {
    private static Map<String,String> fieldPatterMapper = null;
    static {
        fieldPatterMapper = FieldPatternMapper.getMapper();
    }
   /* KeyInChecker(Map<String,String> mapper) {
        fieldPatterMapper = mapper;

    }

    KeyInChecker() {
        fieldPatterMapper = FieldPatternMapper.getMapper();

    }*/


    public static boolean check(String field) {
        if (fieldPatterMapper.containsKey(field)) {
            String pattern = fieldPatterMapper.get(field);

            return field.matches(pattern);

        }
        else {
            return true;
        }

    }
}
