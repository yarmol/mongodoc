package me.jarad.mongo.Dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.bson.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Properties;

/**
 * Created by vitaly on 30.10.2015.
 */
public class ConnectionDao {
    private MongoClient   client    = null;
    private MongoDatabase db        = null;
    private String collectionName   = null;
    private Morphia morph                   = null;

    public Morphia getMorph() {
        return morph;
    }

    public void setMorph(Morphia morph) {
        this.morph = morph;
    }

    public Datastore getDs() {
        return ds;
    }

    public void setDs(Datastore ds) {
        this.ds = ds;
    }

    private Datastore ds                    = null;

    public ConnectionDao() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.conf.xml");
        //client  = (MongoClient) ctx.getBean("mongoClient");
        DbFactory factory = (DbFactory) ctx.getBean("mongoDb");
        db      = factory.getDatabaseConnection();
        client  = factory.getMongoClient();

        morph   = (Morphia)ctx.getBean("morphia");
        ds      = (Datastore)morph.createDatastore(client,db.getName());


    }

    public MongoCollection<Document> getCollection(String collectionName) {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        return collection;
    }

    public MongoCollection<Document> getCollection() {
        collectionName = this.collectionName;
        MongoCollection<Document> collection = db.getCollection(collectionName);
        return collection;
    }

    public MongoClient getClient() {
        return client;
    }

    public void setClient(MongoClient client) {
        this.client = client;
    }

    public MongoDatabase getDb() {
        return db;
    }

    public void setDb(MongoDatabase db) {
        this.db = db;
    }
}
