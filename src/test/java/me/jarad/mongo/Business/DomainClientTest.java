package me.jarad.mongo.business;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import me.jarad.mongo.dao.ConnectionDao;
import me.jarad.mongo.persistance.DocumentEntity;
import me.jarad.mongo.persistance.DocumentRowsEntity;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by vitaly on 24.11.2015.
 */
public class DomainClientTest {

    Configuration conf                   = new Configuration();
    ConnectionDao connectionDao          = new ConnectionDao();
    MongoClient client                   = connectionDao.getClient();
    MongoDatabase db                     = connectionDao.getDb();
    Morphia morph                        = connectionDao.getMorph();
    Datastore  ds                        = connectionDao.getDs();



    @Test
    public void testQuery() throws Exception {



    }

    //@Test
    public void testAdd() throws Exception {


       /* Configuration conf                   = new Configuration();
        ConnectionDao connectionDao          = new ConnectionDao();
        MongoClient client                   = connectionDao.getClient();
        MongoDatabase db                     = connectionDao.getDb();
        Morphia morph                        = connectionDao.getMorph();
        Datastore  ds                        = connectionDao.getDs();
*/

        //Query<DocumentEntity> query = ds.createQuery(DocumentEntity.class);




        DocumentEntity doc = new DocumentEntity();
        doc.setAuthor("Ivanov Ivan");
        doc.setNumber(2123);
        doc.setKind("Transfering act");

        DocumentRowsEntity row = new DocumentRowsEntity();
         row.setRow_id(0);
         row.setInvent("Wooden table");
         row.setCount(25);
         row.setPrice(65.78);
         row.setSum(row.getCount() * row.getPrice());

        ArrayList<DocumentRowsEntity> table = new ArrayList<>();
        table.add(row);

        doc.setGrid(table);

        morph.map(DocumentEntity.class);
        ds.save(doc);


        System.out.println(doc.toString());



        assertTrue(doc != null);

    }
}