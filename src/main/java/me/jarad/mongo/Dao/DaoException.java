package me.jarad.mongo.Dao;

/**
 * Created by vitaly on 04.11.2015.
 */
public class DaoException extends Throwable {
    private String errorDescription;


    public DaoException() {
        super();
    }

    public DaoException(String desc) {
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
