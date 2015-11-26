package me.jarad.mongo.dto;

import me.jarad.mongo.business.DomainObject;
import me.jarad.mongo.persistance.EntityObject;
import me.jarad.mongo.service.BasementObject;
import me.jarad.mongo.view.ViewObject;
import org.bson.BSON;
import org.bson.Document;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by vitaly on 25.11.2015.
 */
public class Track {

    public enum TypeTrack {
        ENTITY, DOMAIN, VIEW
    }

    private BasementObject object;

    public BasementObject getObject() {
        return object;
    }

    public void setObject(BasementObject object) {
        this.object = object;
    }

    public TypeTrack getType() {
        return type;
    }

    public void setType(TypeTrack type) {
        this.type = type;
    }

    private TypeTrack type = null;

    TreeMap<String,Object> attributes = null;



    private void init() {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String fieldName    = field.getName();
            String getterName   = "get"+fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
            Object value           = null;
            try {
                value = clazz.getMethod(getterName).invoke(object);
            }
            catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                continue;
            }

            attributes.put(fieldName,value);
        }

    }


    public Track(EntityObject entityObject) {

        type        = TypeTrack.ENTITY;
        object      = entityObject;
        init();
    }


    public Track(DomainObject domainObject) {

        type        = TypeTrack.DOMAIN;
        object      = domainObject;
        init();

    }


    public Track(ViewObject viewObject) {
        type        = TypeTrack.VIEW;
        object      = viewObject;
        init();
    }



    private Object cast(Class clazz) {
        Object castedObject = null;
        try {

            castedObject = clazz.cast(Class.forName(clazz.getName()).newInstance());

            for (String fieldName : attributes.keySet()) {
                Field field = null;
                Object value = null;
                try {

                    field = clazz.getField(fieldName);
                    value = attributes.get(fieldName);

                }
                catch (NoSuchFieldException e) {
                    continue;
                }

                String setterName = "set"+fieldName;

                try {
                    clazz.getMethod(setterName).invoke(castedObject,value);
                }
                catch (NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }


                return  clazz.cast(castedObject);
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();

        }

        return clazz.cast(castedObject);
    }


    public DomainObject toDomain(Class clazz) {

      return (DomainObject)cast(clazz);

    }

    public ViewObject toView(Class clazz) {
        return (ViewObject)cast(clazz);
    }

    public EntityObject toEntity(Class clazz) {
        return (EntityObject)cast(clazz);
    }


    public String toString() {
        Document doc = new Document();
        for (String key : attributes.keySet()) {
            Object value = attributes.get(key);

            doc.append(key,value);

        }

        return doc.toJson();

    }




}
