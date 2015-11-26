package me.jarad.mongo.dto;

import me.jarad.mongo.business.DomainObject;
import me.jarad.mongo.dao.ObjectDao;
import me.jarad.mongo.persistance.EntityObject;
import me.jarad.mongo.service.BasementObject;
import me.jarad.mongo.view.ViewObject;
import org.bson.BSON;
import org.bson.Document;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by vitaly on 25.11.2015.
 */
public class Track {

    public enum TypeTrack {
        ENTITY, DOMAIN, VIEW
    }

    private BasementObject object;
    private TypeTrack type = null;
    private TreeMap<String,Object> attributes = new TreeMap<>();

    public TypeTrack getType() {
        return type;
    }
    public void setType(TypeTrack type) {
        this.type = type;
    }
    public TreeMap<String, Object> getAttributes() {
        return attributes;
    }
    public void setAttributes(TreeMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Track(BasementObject object) {
        if (object instanceof  EntityObject) {
            type        = TypeTrack.ENTITY;
        }
        else  if (object instanceof DomainObject) {
            type        = TypeTrack.DOMAIN;
        }
        else  if (object instanceof ViewObject) {
            type        = TypeTrack.VIEW;
        }

        this.object      = object;
        DtoFactory.fillAttributes(this.object, attributes);
    }


    public String toString() {
        Document doc = DtoFactory.toDocument(attributes);
        return doc.toJson();
    }


}
