package me.jarad.mongo.Dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vitaly on 04.11.2015.
 */
public enum Operators {


        EQUAL("=","$eq"),
        NOT_EQUAL("!=","$ne"),
        LESS_THAN("<","$lt"),
        GREATER_THEN(">","$gt"),
        LESS_THAN_OR_EQUAL("<=","$lte"),
        GREATER_THEN_OR_EQUAL(">=","$gte");

        private String view;
        private String bsonOperator;

        Operators(String string,String bsonOperator) {
            this.view = string;
            this.bsonOperator = bsonOperator;
        }

        public String getView() {
            return view;
        }

        public String getBson() {
            return bsonOperator;
        }

        public static String getBsonByView(String viewString) {
            List<Operators> list = Arrays.asList(Operators.values());
            Map<String,Operators> mapper = new HashMap<>();
            for (Operators element : list) {
                mapper.put(element.getView(),element);
            }
            return mapper.get(viewString).getBson();


        }


}
