package me.jarad.mongo.dao;

import me.jarad.mongo.dao.gears.StrategyDao;
import me.jarad.mongo.dao.morphia.DocumentMorphiaDao;

import java.util.HashMap;

/**
 * Created by vitaly on 25.11.2015.
 */
public class FactoryDao {

    private static HashMap<StrategyDao, String> mapperDocuments;
    private static HashMap<StrategyDao, String> mapperUsers;
    static  {
        mapperDocuments.put(StrategyDao.NATIVE,"DocumentNativeDao");
        mapperDocuments.put(StrategyDao.MORPHIA,"DocumentMorphiaDao");
        mapperUsers.put(StrategyDao.MORPHIA,"UserMorphiaDao");
        mapperUsers.put(StrategyDao.NATIVE,"UserNativeDao");
    }

    public static ObjectDao getDaoProvider(HashMap<StrategyDao, String> map, StrategyDao strategyDao) {
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
