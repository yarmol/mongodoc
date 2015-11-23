package me.jarad.mongo.Dao;

import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import me.jarad.mongo.service.SessionService;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.*;

/**
 * Created by vitaly on 04.11.2015.
 */
public class DocumentDao extends  AbstractDao {
    public void setListDocSize(int listDocSize) {
        this.listDocSize = listDocSize;
    }

    public int getListDocSize() {
        return listDocSize;
    }

    private int listDocSize = 50;

    public DocumentDao() {
        super();
        setDomainCollection(database.getCollection("documents"));
    }




    public List<Document> getDocuments() {
        return getList();
    }

    public LinkedList<Document> getDocs(String... projection) {
        Bson selector = Projections.fields(Projections.include(projection));
        LinkedList<Document> list = getDomainCollection().find().
                projection(selector).
                sort(Sorts.ascending("number")).limit(getListDocSize()).
                into(new LinkedList<>());
        return list;
    }


    public Document getDocument(ObjectId id) {
        return this.get(id);
    }

    public static String getKind(int val) {

        if (val == 0 ) {
            return "Incoming act";
        }
        else if (val == 1) {
            return "Outgoing act";
        }
        else if (val == 2) {
            return "Transfering act";
        }
        else {
            return "Service act";
        }


    }

    public boolean addDocument(int numberOfDoc, int kind, List<Document> table) {
        Document doc = new Document();
        doc.append("number",numberOfDoc);
        doc.append("kind",getKind(kind));
        doc.append("author", SessionService.getCurrentUser());
/*
        List<Document> table = new ArrayList<Document>();

            double sumAmount = 0;
            for (int j = 0; j < (rowCountSeq.nextInt(10)+1); j++) {
                Document row = new Document();
                row.append("row_id", j );
                row.append("invent", DataTransmitter.getFullInvent());
                int q = DataTransmitter.getCount();
                double price = DataTransmitter.getPrice();
                double sum  = price*q;
                row.append("count",q);
                row.append("price",price);
                row.append("sun",sum);
                sumAmount += sum;
                table.add(row);
            }*/
        double sumAmount = 0;
        for(Document d : table) {
            double value = (double)d.get("sun");
            sumAmount += value;
        }

        doc.append("grid",table);
        doc.append("doc_sum",sumAmount);
        add(getDomainCollection(), doc);
        return  true;
    }

}
