package me.jarad.mongo.dao.nat;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import me.jarad.mongo.dao.AbstractInitDao;
import me.jarad.mongo.dao.ObjectDao;
import me.jarad.mongo.dao.gears.Operators;
import me.jarad.mongo.dao.gears.StrategyDao;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaly on 04.11.2015.
 */
public abstract class AbstractNativeDao extends AbstractInitDao implements ObjectDao<Document> {

    protected MongoCollection<Document> collection      = null;

    AbstractNativeDao() {
        super();
        this.setStrategy(StrategyDao.NATIVE);
    }


    protected static ArrayList<String> docListToStringList(List<Document> list, String col) {
        ArrayList<String> result = new ArrayList<>();
        for (Document doc : list) {
            result.add((String)doc.get(col));
        }
        return result;
    }


    /**
     * Methods to add docmunet to collection
     * @param collection
     * @param doc
     */
    public void add(String collection, Document doc) {
        MongoCollection<Document> currentCollection = database.getCollection(collection);
        currentCollection.insertOne(doc);
    }

    public void add(MongoCollection<Document> collection, Document doc){
        collection.insertOne(doc);
    }

    public void add(Document doc) {

        getCollection().insertOne(doc);
    }

    //protected void add(EntityObject entity) {
    //    ds.save(entity);
    //}

    //protected void add(T entityDoc) {
    //   if (typeDescriptor == EntityObject.class) {
    //       add((EntityObject)entityDoc);
    //   }
    //   else if (typeDescriptor == Document.class) {
    //       add((Document)entityDoc);
    //   }
    //}


    /**
     * Methods to get documents
     * @param exclude
     * @return
     */

    public Document get(String id) {
        Bson filter = Filters.eq("_id", id);
        return getCollection().find(filter).first();
    }

    public Document get(ObjectId id) {
        Bson filter = Filters.eq("_id", id);
        return getCollection().find(filter).first();
    }


    public List<Document> getList(String exclude) {
        Bson projection = Projections.exclude(exclude);
        ArrayList<Document> listOfUsers = getCollection().find().projection(projection).into(new ArrayList<Document>());
        return listOfUsers;
    }

    public List<Document> getList() {

        ArrayList<Document> listOfUsers = getCollection().find().into(new ArrayList<Document>());
        return listOfUsers;
    }



    public ArrayList<Document> getFilteredList(String key,String operatorView, String value) {
        Document documentCondition = new Document(key,value);
        Bson filter = new Document(Operators.getBsonByView(operatorView),documentCondition);
        ArrayList<Document> resultList = getCollection().find(filter).into(new ArrayList<Document>());
        return resultList;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public void setCollection(MongoCollection<Document> domainCollection) {
        this.collection = domainCollection;
    }

    public void setCollectionName(String collectionName) {
        this.setCollectionName(collectionName);
        setCollection(database.getCollection(collectionName));

    }


}
