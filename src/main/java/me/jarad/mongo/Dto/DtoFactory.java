package me.jarad.mongo.dto;


import me.jarad.mongo.business.pojo.DomainObject;
import me.jarad.mongo.persistance.*;
import me.jarad.mongo.view.*;
import me.jarad.mongo.service.*;
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


    public static Track.TypeTrack getType(BasementObject object) {
        Track.TypeTrack type = null;
        if (object instanceof EntityObject) {
            type = Track.TypeTrack.ENTITY;
        } else if (object instanceof DomainObject) {
            type = Track.TypeTrack.DOMAIN;
        } else if (object instanceof ViewObject) {
            type = Track.TypeTrack.VIEW;
        }
        return type;
    }

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
    @Deprecated
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

    /**
     * Fill map with attributes of object
     * Every iterable attribute represented as List internal Map.
     *
     * @param obj
     * @param attrs
     */
    public static void fillAttributesMap(BasementObject obj, Map<String,Object> attrs) {



        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            String getterName = DtoFactory.getAccessorName.apply("get", fieldName);

            Object value = null;

            try {
                value = clazz.getMethod(getterName).invoke(obj);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                continue;
            }


            Map<String,Object> rowDocument = null;

            if (value instanceof List) {

                Iterator listIterator = ((List) value).iterator();
                List<Map<String,Object>> table = new ArrayList<>();
                while (listIterator.hasNext()) {

                    BasementObject listValue     = (BasementObject)listIterator.next();
                    Class clazzListValue         = listValue.getClass();


                    rowDocument = new HashMap<>();
                    fillAttributesMap(listValue, rowDocument);
                    table.add(rowDocument);

                }

                attrs.put(fieldName, table);



            } else {
                attrs.put(fieldName, value);
            }


        }

    }



    public static void fillObject (Map<String,Object> attributes, BasementObject object) {




        for (String key : attributes.keySet()) {

            if (key == "DETERMINATOR") {
                continue;
            }

            Object value = attributes.get(key);

            Class clz = object.getClass();


            Method method = null;
            try {
                    Field field         = clz.getDeclaredField(key);
                    Class fieldType     = field.getType();
                    String setterName   = getAccessorName.apply("set",key);
                    method              = clz.getMethod(setterName,fieldType);
               }
               catch (NoSuchMethodException  | NoSuchFieldException e  ) {
                   e.printStackTrace();
                   break;
               }

            if (value instanceof List) {
                System.out.println("list:");
                List<BasementObject> table  = new ArrayList<>();
                Iterator listIterator       = ((List) value).iterator();
                //BasementObject rowDocument  = null;
                while (listIterator.hasNext()) {

                    Map<String,Object> listValue        = (Map<String,Object>)listIterator.next();

                    Class  clazzListValue               = listValue.getClass();
                    String basicParentClassName         = object.getClass().getName();
                    String type                         = getType(object).toString();

                    String suffix                       = firstLetterUpper(type, true);
                    String relationalClassName          = basicParentClassName.replace(suffix,"Rows"+suffix);

                    BasementObject relationalInstance   = null;

                    try {
                        relationalInstance = (BasementObject)Class.forName(relationalClassName).newInstance();
                    }
                    catch (ClassNotFoundException| InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                        break;
                    }

                    fillObject(listValue, relationalInstance);

                    table.add(relationalInstance);
                }

                try {
                    method.invoke(object,table);

                }
                catch (IllegalAccessException | InvocationTargetException e ) {
                    e.printStackTrace();
                    break;
                }

            }
            else {
                try {
                    method.invoke(object,value);
                }
                catch (IllegalAccessException | InvocationTargetException e ) {
                    break;
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
                    Map<String,Object> listValue = (Map<String,Object>)listIterator.next();
                    Class  clazzListValue = listValue.getClass();
                    rowDocument = toDocument(listValue);
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

    public static EntityObject toEntity(Track dto, Class clazz) {
        EntityObject entityObject = null;
        try {
            entityObject = (EntityObject)clazz.newInstance();
        }
        catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        fillObject(dto.getAttributes(), entityObject);

        return entityObject;
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
