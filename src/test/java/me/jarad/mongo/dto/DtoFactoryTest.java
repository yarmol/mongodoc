package me.jarad.mongo.dto;

import me.jarad.mongo.business.DocumentDomain;
import me.jarad.mongo.dao.FactoryDao;
import me.jarad.mongo.dao.ObjectDao;
import me.jarad.mongo.dao.gears.StrategyDao;
import me.jarad.mongo.persistance.DocumentEntity;
import me.jarad.mongo.persistance.EntityObject;
import me.jarad.mongo.service.BasementObject;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by vitaly on 26.11.2015.
 */
public class DtoFactoryTest {

    @Test
    public void testFillObject() throws Exception {
        ObjectDao<DocumentEntity> daoDocument = FactoryDao.getDaoProviderDocument(StrategyDao.MORPHIA);
        EntityObject document =   daoDocument.getbyField("number", 256);


        Track dtoDoc = new Track(document);

        Map<String,Object> attributes = dtoDoc.getAttributes();

        DocumentDomain object = new DocumentDomain();

        for (String key : attributes.keySet()) {

            Object value = attributes.get(key);
            System.out.println( key + "=" + value) ;
            Class clz = object.getClass();
            Method method = null;
            try {
                Field field = clz.getDeclaredField(key);
                Class fieldType = field.getType();
                System.out.println("  setter find       =" + key) ;
                String setterName    = DtoFactory.getAccessorName.apply("set",key);
                System.out.println("  setter        =" + setterName) ;
                method = clz.getMethod(setterName,fieldType);


            }
            catch (NoSuchMethodException  e  ) {
                e.printStackTrace();
                break;
            }

            if (value instanceof List) {
                System.out.println( key + " value = list") ;
                List<BasementObject> table  = new ArrayList<>();
                Iterator listIterator       = ((List) value).iterator();
                BasementObject rowDocument  = null;
                while (listIterator.hasNext()) {

                    Track listValue        = (Track)listIterator.next();
                    Class  clazzListValue   = listValue.getClass();
                    String basicParentClassName  = object.getClass().getName();
                    String suffix = DtoFactory.firstLetterUpper(listValue.getType().toString(), true);
                    String relationalClassName   = basicParentClassName.replace(suffix,"Rows"+suffix);

                    System.out.println(" listValue = " + listValue.toString()) ;
                    System.out.println(" relationalClassName = " + relationalClassName) ;
                    BasementObject relationalInstance = null;

                    try {
                        relationalInstance = (BasementObject)Class.forName(relationalClassName).newInstance();
                    }
                    catch (ClassNotFoundException| InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                        break;
                    }

                    continue;
                    //fillObject(((Track)listValue).getAttributes(),relationalInstance);
                    //table.add(rowDocument);
                }


            }
            else {
                try {
                    System.out.println(" set = " + value) ;
                    method.invoke(object,value);
                }
                catch (IllegalAccessException | InvocationTargetException e ) {
                    continue;
                }
            }
        }

        System.out.println("domain = " + object.toString());

        assertTrue(object != null);
    }

    @Test
    public void testToDomain() throws Exception {

    }
}