package me.jarad.mongo.Bo;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import me.jarad.mongo.Dao.ConnectionDao;
import org.bson.types.CodeWScope;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by vitaly on 24.11.2015.
 */
public class DomainClientTest {

    @Test
    public void testAdd() throws Exception {


        Configuration conf                   = new Configuration();
        ConnectionDao connectionDao          = new ConnectionDao();
        MongoClient client                   = connectionDao.getClient();
        MongoDatabase db                     = connectionDao.getDb();
        Morphia morph                        = connectionDao.getMorph();
        Datastore  ds                        = connectionDao.getDs();


        //Query<DocumentDomainObject> query = ds.createQuery(DocumentDomainObject.class);




        DocumentDomainObject doc = new DocumentDomainObject();
        doc.setAuthor("Ivanov Ivan");
        doc.setNumber(2123);
        doc.setKind("Transfering act");

        GridDomainObject row = new GridDomainObject();
         row.setRow_id(0);
         row.setInvent("Wooden table");
         row.setCount(25);
         row.setPrice(65.78);
         row.setSum(row.getCount() * row.getPrice());

        ArrayList<GridDomainObject> table = new ArrayList<>();
        table.add(row);

        doc.setGrid(table);

        morph.map(DocumentDomainObject.class);
        ds.save(doc);


        System.out.println(doc.toString());



        assertTrue(doc != null);

    }
}