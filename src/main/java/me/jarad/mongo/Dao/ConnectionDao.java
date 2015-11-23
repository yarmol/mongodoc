package me.jarad.mongo.Dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
    private MongoClient   client = null;
    private MongoDatabase db = null;
    private String collectionName = null;

    public ConnectionDao() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.conf.xml");
        //client  = (MongoClient) ctx.getBean("mongoClient");
        DbFactory factory = (DbFactory) ctx.getBean("mongoDb");
        db      = factory.getDatabaseConnection();
        client  = factory.getMongoClient();
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
