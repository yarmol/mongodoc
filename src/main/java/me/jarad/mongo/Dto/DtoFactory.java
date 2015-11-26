package me.jarad.mongo.dto;

import me.jarad.mongo.business.DocumentRowsDomain;
import me.jarad.mongo.business.DomainObject;
import me.jarad.mongo.persistance.EntityObject;
import me.jarad.mongo.service.BasementObject;
import org.bson.Document;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.*;

/**
 * Created by vitaly on 26.11.2015.
 */
public class DtoFactory {


    public static String firstLetterUpper(String word, boolean withLowerCase) {
        String firstLetter =  word.substring(0, 1).toUpperCase();
        String leftovers;
        if (withLowerCase) {
            leftovers =    word.substring(1).toLowerCase();
        }
        else {
            leftovers =    word.substring(1);
        }

        return firstLetter+leftovers;
    }

    static public BinaryOperator<String> getAccessorName  =
            ((s1,s2) -> {
                return s1+firstLetterUpper(s2,false);
            });

    /**
     * Creates map with filled attributes of object
     * Resolve internal objects into basic dto view (as Track object)
     * @param attributes
     * @param object
     * @return
     */
    public static void fillAttributes(BasementObject object, Map<String,Object> attributes) {
        Class clazz             = object.getClass();
        Field[] fields          = clazz.getDeclaredFields();

        for (Field field : fields) {
            String fieldName        = field.getName();
            String getterName       = getAccessorName.apply("get", fieldName);

            Object value            = null;

            try {
                value = clazz.getMethod(getterName).invoke(object);
            }
            catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                continue;
            }

            Track rowDocument = null;

            if (value instanceof List) {

                Iterator listIterator = ((List) value).iterator();
                List<Track> table = new ArrayList<>();
                while (listIterator.hasNext()) {
                    Object listValue = listIterator.next();
                    Class  clazzListValue = listValue.getClass();

                    rowDocument = new Track((BasementObject)listValue);
                    table.add(rowDocument);
                }

                attributes.put(fieldName, table);

            }
            else {
                attributes.put(fieldName, value);
            }


        }

    }



    @SuppressWarnings("unchecked")
    public static void fillObject (Map<String,Object> attributes, BasementObject object) {
        for (String key : attributes.keySet()) {

            Object value = attributes.get(key);
            System.out.println( key + "=" + value) ;
            Class clz = object.getClass();
            Method method = null;
            try {

                    String setterName    = getAccessorName.apply("set",key);
                    method = clz.getMethod(setterName);
                    System.out.println("  setter        =" + setterName) ;

               }
               catch (NoSuchMethodException  e  ) {
                   continue;
               }

            if (value instanceof List) {
                List<BasementObject> table  = new ArrayList<>();
                Iterator listIterator       = ((List) value).iterator();
                BasementObject rowDocument  = null;
                while (listIterator.hasNext()) {

                    Track listValue        = (Track)listIterator.next();
                    Class  clazzListValue   = listValue.getClass();
                    String basicParentClassName  = object.getClass().getName();
                    String suffix = firstLetterUpper(listValue.getType().toString(),true);
                    String relationalClassName   = basicParentClassName.replace(suffix,"Rows"+suffix);
                    BasementObject relationalInstance = null;

                    try {
                        relationalInstance = (BasementObject)Class.forName(relationalClassName).newInstance();
                    }
                    catch (ClassNotFoundException| InstantiationException | IllegalAccessException e) {
                        continue;
                    }

                    fillObject(((Track)listValue).getAttributes(),relationalInstance);
                    table.add(rowDocument);
                }


            }
            else {
                try {
                    method.invoke(object,value);
                }
                catch (IllegalAccessException | InvocationTargetException e ) {
                    continue;
                }
            }
        }

    }


    public static Document toDocument(Map<String,Object> attributes) {
        Document doc = new Document();
        for (String key : attributes.keySet()) {
            Object value = attributes.get(key);
            Document rowDocument = null;

            if (value instanceof List) {
                Iterator listIterator = ((List) value).iterator();
                List<Document> table = new ArrayList<>();
                while (listIterator.hasNext()) {
                    Track listValue = (Track)listIterator.next();
                    Class  clazzListValue = listValue.getClass();

                    rowDocument = toDocument(listValue.getAttributes());
                    table.add(rowDocument);
                }

                doc.append(key, table);

            }
            else {
                doc.append(key,value);
            }

        }
        return doc;
    }

    public static EntityObject toEntity() {
        return null;
    }

    public static DomainObject toDomain(Track dto, Class clazz) {
        DomainObject domainObject = null;
        try {
            domainObject = (DomainObject)clazz.newInstance();
        }
        catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        fillObject(dto.getAttributes(), domainObject);

        return domainObject;
    }

}
