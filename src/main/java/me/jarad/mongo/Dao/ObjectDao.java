package me.jarad.mongo.dao;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by vitaly on 25.11.2015.
 */
public interface ObjectDao<T> {

    void add(T doc);



    Iterable<T> getIterator();

    //List<T> getList(String[] ids);

    List<T> getList(int limit, int skip);

    List<T> getList(int limit);

    T get(String id);

    T get(Object id);

}
