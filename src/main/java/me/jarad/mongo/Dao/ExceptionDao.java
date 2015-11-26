package me.jarad.mongo.dao;

/**
 * Created by vitaly on 04.11.2015.
 */
public class ExceptionDao extends Throwable {
    private String errorDescription;


    public ExceptionDao() {
        super();
    }

    public ExceptionDao(String desc) {
        super();
        errorDescription = desc;

    }


    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }


}
