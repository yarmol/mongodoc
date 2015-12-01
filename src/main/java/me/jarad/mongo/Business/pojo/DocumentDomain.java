package me.jarad.mongo.business.pojo;

import me.jarad.mongo.view.Views;
import org.bson.types.ObjectId;

import java.util.ArrayList;

/**
 * Created by vitaly on 25.11.2015.
 */

public class DocumentDomain implements DomainObject {


    private ObjectId id;
    private int number;
    private String kind;
    private String author;
    private double docSum;
    private ArrayList<DocumentRowsDomain> grid;

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
    public ArrayList<DocumentRowsDomain> getGrid() {
        return grid;
    }


    public void setGrid(ArrayList<DocumentRowsDomain> grid) {
        this.grid = grid;
    }

    public DocumentDomain() {

    }



    @Override
    public String toString() {
        return Views.toStringView(this);

    }


}
