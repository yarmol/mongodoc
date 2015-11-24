package me.jarad.mongo.Bo;

import org.mongodb.morphia.annotations.*;

/**
 * Created by vitaly on 24.11.2015.
 */

@Entity
public class GridDomainObject {

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

}
