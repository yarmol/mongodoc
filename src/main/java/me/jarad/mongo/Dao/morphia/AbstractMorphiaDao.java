package me.jarad.mongo.dao.morphia;

import com.google.common.reflect.TypeToken;
import me.jarad.mongo.dao.AbstractInitDao;
import me.jarad.mongo.dao.ObjectDao;
import me.jarad.mongo.dao.gears.StrategyDao;
import me.jarad.mongo.persistance.DocumentEntity;
import me.jarad.mongo.persistance.EntityObject;
import org.mongodb.morphia.query.MorphiaIterator;
import org.mongodb.morphia.query.Query;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by vitaly on 25.11.2015.
 */
public class AbstractMorphiaDao<EntityObject> extends AbstractInitDao implements ObjectDao<EntityObject> {

    public Class getClassDescriptor() {
        return classDescriptor;
    }

    public void setClassDescriptor(Class classDescriptor) {
        this.classDescriptor = classDescriptor;
    }

    protected Class classDescriptor;

    AbstractMorphiaDao() {
        super();
        this.setStrategy(StrategyDao.MORPHIA);
    }


    @Override
    public void add(EntityObject doc) {

        getDatastore().save(doc);

    }


    @Override
    public Iterable<EntityObject> getIterator() {
        Query<EntityObject> query = getDatastore().createQuery(getClassDescriptor());
        Iterable<EntityObject> iterator = query.fetch();
        return iterator;
    }

    @Override
    public List<EntityObject> getList(int limit, int skip) {

        Query<EntityObject> query = getDatastore().createQuery(getClassDescriptor());
        return query.limit(limit).offset(skip).asList();


    }

    @Override
    public List<EntityObject> getList(int limit) {

       return getList(limit,0);


    }

    @Override
    public EntityObject get(String id) {

        Query<EntityObject> query = getDatastore().createQuery(getClassDescriptor());
        return query.field("_id").equal(id).get();

    }

    @Override
    public EntityObject get(Object id) {

        Query<EntityObject> query = getDatastore().createQuery(getClassDescriptor());
        return query.field("_id").equal(id).get();

    }
}
