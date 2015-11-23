package me.jarad.mongo.Dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaly on 04.11.2015.
 */
public abstract class AbstractDao {
    protected MongoDatabase database = null;
    private MongoCollection<Document> domainCollection = null;

    AbstractDao() {
        database = new ConnectionDao().getDb();

    }

    protected static ArrayList<String> docListToStringList(List<Document> list, String col) {
        ArrayList<String> result = new ArrayList<>();
        for (Document doc : list) {
            result.add((String)doc.get(col));
        }
        return result;
    }


    protected void add(String collection, Document doc) {
        MongoCollection<Document> currentCollection = database.getCollection(collection);
        currentCollection.insertOne(doc);
    }

    protected void add(MongoCollection<Document> collection, Document doc){
        collection.insertOne(doc);
    }

    protected void add(Document doc) {

        getDomainCollection().insertOne(doc);
    }

    public List<Document> getList(String exclude) {
        Bson projection = Projections.exclude(exclude);
        ArrayList<Document> listOfUsers = getDomainCollection().find().projection(projection).into(new ArrayList<Document>());
        return listOfUsers;
    }

    public List<Document> getList() {

        ArrayList<Document> listOfUsers = getDomainCollection().find().into(new ArrayList<Document>());
        return listOfUsers;
    }

    public Document get(String id) {
        Bson filter = Filters.eq("_id", id);
        return getDomainCollection().find(filter).first();
    }


    public Document get(ObjectId id) {
        Bson filter = Filters.eq("_id", id);
        return getDomainCollection().find(filter).first();
    }

    public ArrayList<Document> getFilteredList(String key,String operatorView, String value) {
        Document documentCondition = new Document(key,value);
        Bson filter = new Document(Operators.getBsonByView(operatorView),documentCondition);
        ArrayList<Document> resultList = getDomainCollection().find(filter).into(new ArrayList<Document>());
        return resultList;
    }

    public MongoCollection<Document> getDomainCollection() {
        return domainCollection;
    }

    public void setDomainCollection(MongoCollection<Document> domainCollection) {
        this.domainCollection = domainCollection;
    }
}
