package me.jarad.mongo.persistance;

import com.mongodb.DBObject;
import me.jarad.mongo.view.Views;
import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;

/**
 * Created by vitaly on 24.11.2015.
 */

@Entity
public class DocumentRowsEntity implements EntityObject {

    @Id
    private int row_id;

    private  String invent;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public String getInvent() {
        return invent;
    }

    public void setInvent(String invent) {
        this.invent = invent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sun) {
        this.sum = sun;
    }

    private int count;
    private double price;

    @Property("sun")
    private double sum;

    public String toString() {
        return Views.toStringView(this);
    }

    @PreSave()
    public void beforeSave(DBObject dbObj) {

        int count = (int)dbObj.get("count");
        double price = (double)dbObj.get("price");

        dbObj.put("sun", count * price);


    }

}
