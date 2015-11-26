package me.jarad.mongo.dao;

import me.jarad.mongo.dao.*;
import me.jarad.mongo.dao.gears.*;
import me.jarad.mongo.dao.morphia.*;
import me.jarad.mongo.dao.nat.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by vitaly on 25.11.2015.
 */
public class FactoryDao {

    private static Map<StrategyDao, String> mapperDocuments = new HashMap<>();
    private static Map<StrategyDao, String> mapperUsers = new HashMap<>();
    static  {
        mapperDocuments.put(StrategyDao.NATIVE,"me.jarad.mongo.dao.nav.DocumentNativeDao");
        mapperDocuments.put(StrategyDao.MORPHIA,"me.jarad.mongo.dao.morphia.DocumentMorphiaDao");
        mapperUsers.put(StrategyDao.MORPHIA,"me.jarad.mongo.dao.morphia.UserMorphiaDao");
        mapperUsers.put(StrategyDao.NATIVE,"me.jarad.mongo.dao.nav.UserNativeDao");
    }

    public static ObjectDao getDaoProvider(Map<StrategyDao, String> map, StrategyDao strategyDao) {
        String className = map.get(strategyDao);

        ObjectDao objectDao = null;

        try {

            Class classPresent = Class.forName(className);

            objectDao = (ObjectDao)classPresent.newInstance();

        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return objectDao;

    }

    public static ObjectDao getDaoProviderDocument(StrategyDao strategyDao) {

       return getDaoProvider(mapperDocuments,strategyDao);

    }




}
