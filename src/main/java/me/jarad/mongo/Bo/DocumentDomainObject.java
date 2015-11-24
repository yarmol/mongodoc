package me.jarad.mongo.Bo;

import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;

/**
 * Created by vitaly on 24.11.2015.
 */

@Entity(value = "document_second", noClassnameStored = true)
public class DocumentDomainObject {

    @Id
    private ObjectId id;

    private int number;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getDocSum() {
        return docSum;
    }

    public void setDocSum(double docSum) {
        this.docSum = docSum;
    }

    public ArrayList<GridDomainObject> getGrid() {
        return grid;
    }

    public void setGrid(ArrayList<GridDomainObject> grid) {
        this.grid = grid;
    }

    private String kind;




    private String author;

    @Property("doc_sum")
    private double docSum;

    @Embedded
    private ArrayList<GridDomainObject> grid;

    @PreSave()
    public void beforeSave(DBObject dbObj) {
        System.out.println(dbObj.toString());

        ArrayList<DBObject> docGrid = (ArrayList)dbObj.get("grid");
        int sum = 0;
        for (DBObject row : docGrid) {

             sum += (int)row.get("sum");

        }
        dbObj.put("docSum",(int)sum);
    }




}
