package me.jarad.mongo.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.jarad.mongo.dao.gears.StrategyDao;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by vitaly on 25.11.2015.
 */
public abstract  class AbstractInitDao {

    protected MongoDatabase database                    = null;
    protected Datastore datastore                       = null;
    protected Morphia morphia                           = null;

    public Morphia getMorphia() {
        return morphia;
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }

    protected String collectionName                     = null;

    public StrategyDao getStrategy() {
        return strategy;
    }

    public void setStrategy(StrategyDao strategy) {
        this.strategy = strategy;
    }

    protected StrategyDao strategy = null;

    public AbstractInitDao() {
        ConnectionDao connectionDao = new ConnectionDao();
        database                    = connectionDao.getDb();
        morphia                     = connectionDao.getMorph();
        datastore                   = connectionDao.getDs();
    }

    public void setCollectionName(String collectionName) {

        this.collectionName = collectionName;
    }



}
