package me.jarad.mongo.dto;

import me.jarad.mongo.service.BasementObject;
import org.bson.Document;

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

        type = DtoFactory.getType(object);


        this.object      = object;
        DtoFactory.fillAttributesMap(this.object, attributes);
    }


    public String toString() {
        Document doc = DtoFactory.toDocument(attributes);
        return doc.toJson();
    }


}
