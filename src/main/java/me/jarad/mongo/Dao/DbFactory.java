package me.jarad.mongo.Dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.Map;

/**
 * Created by vitaly on 04.11.2015.
 */
public class DbFactory {

    private MongoClient mongoClient = null;
    private Map<String,String>  connectionProperties = null;
    private String urlConnection = null;
    private String userConnection = null;
    private String passConnection = null;
    private String dbNameConnection = null;


    /*
    Basic constructor with args
     */
    DbFactory(MongoClient client) {
        this.setMongoClient(client);
    }

    /*
    Basic no-arg constructor
     */
    DbFactory() {
    }


    /*
    Factory method to get db instance
     */
    public MongoDatabase getDatabaseConnection(){
        MongoDatabase database  = null;

        //&& (userConnection == null) &&
                //(passConnection == null)
        try {
            if (( mongoClient == null) ||
                    ((urlConnection == null) && (dbNameConnection == null)))  {
                throw new DaoException("Can not initialize db connection");
            }

            database = mongoClient.getDatabase(this.dbNameConnection);



        }
        catch (DaoException e) {
            System.out.println(e.getErrorDescription());
            e.printStackTrace();
        }

        return database;

    }


    /*
    Setters and getters methods
     */
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }


    public String getPassConnection() {
        return passConnection;
    }

    public void setPassConnection(String passConnection) {
        this.passConnection = passConnection;
    }

    public String getUrlConnection() {
        return urlConnection;
    }

    public void setUrlConnection(String urlConnection) {
        this.urlConnection = urlConnection;
    }

    public String getUserConnection() {
        return userConnection;
    }

    public void setUserConnection(String userConnection) {
        this.userConnection = userConnection;
    }

    public String getDbNameConnection() {
        return dbNameConnection;
    }

    public void setDbNameConnection(String dbNameConnection) {
        this.dbNameConnection = dbNameConnection;
    }
}
