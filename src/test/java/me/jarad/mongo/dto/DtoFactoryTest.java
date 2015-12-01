package me.jarad.mongo.dto;

import me.jarad.mongo.business.pojo.DocumentDomain;
import me.jarad.mongo.business.pojo.DocumentRowsDomain;
import me.jarad.mongo.business.pojo.DomainObject;
import me.jarad.mongo.dao.FactoryDao;
import me.jarad.mongo.dao.ObjectDao;
import me.jarad.mongo.dao.gears.StrategyDao;
import me.jarad.mongo.persistance.DocumentEntity;
import me.jarad.mongo.persistance.EntityObject;
import me.jarad.mongo.service.BasementObject;
import org.bson.Document;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by vitaly on 26.11.2015.
 */
public class DtoFactoryTest {

    ObjectDao<DocumentEntity> daoDocument = FactoryDao.getDaoProviderDocument(StrategyDao.MORPHIA);
    EntityObject document =   daoDocument.getbyField("number", 256);

    //@Test
    public void testFillAttributes() throws Exception {
          Map<String,Object> attrs = new HashMap<>();
          DtoFactory.fillAttributes(document,attrs);
          String authorFromDoc = ((DocumentEntity)document).getAuthor();
          String authorFromMap = (String)attrs.get("author");
          int numberFromDoc = ((DocumentEntity)document).getNumber();
          int numberFromMap = (int)attrs.get("number");
          assertTrue((authorFromMap.equals(authorFromMap)) && (numberFromDoc == numberFromMap));
    }

    @Test
    public void testFillAttributesMap() throws Exception {

        BasementObject obj  = new DocumentEntity();
        obj                 = document;

        Map<String,Object> attrs = new HashMap<>();
        DtoFactory.fillAttributesMap(obj, attrs);


        int gridSizeDoc = ((DocumentEntity)document).getGrid().size();
        List<Map<String,Object>> rowList = (List<Map<String,Object>>)attrs.get("grid");

        assertTrue(gridSizeDoc == rowList.size());


    }

    @Test
    public void testToDocument() throws Exception {

        BasementObject obj  = new DocumentEntity();
        obj                 = document;

        Map<String,Object> attrs = new HashMap<>();
        DtoFactory.fillAttributesMap(obj, attrs);

        Document docJson = DtoFactory.toDocument(attrs);

        System.out.println(docJson.toJson());

        Track dtoTrack = new Track(document);
        System.out.println("Dto track from entity:" + dtoTrack.toString());

        assertTrue(docJson != null);
    }

    @Test
    public void testFillObject() throws Exception {
        BasementObject obj  = new DocumentEntity();
        obj                 = document;

        Map<String,Object> attrs = new HashMap<>();
        DtoFactory.fillAttributesMap(obj,attrs);


        DocumentDomain docDomain = new DocumentDomain();



        DtoFactory.fillObject(attrs, docDomain);

        //System.out.println("enter point: " + attrs.size());


       // System.out.println("docDomain : " + docDomain.toString());

        assertTrue((int)docDomain.getNumber() == (int)attrs.get("number") );





    }


    @Test
    public void testToEntity() throws Exception {
        DocumentDomain domDocument = new DocumentDomain();
        domDocument.setAuthor("William Shakespear");
        domDocument.setKind("Transfer act");
        domDocument.setNumber(456);

        DocumentRowsDomain row1 = new DocumentRowsDomain();
            row1.setInvent("Titatium mug");
            row1.setPrice(25);
            row1.setCount(5);
            row1.setRow_id(0);


        DocumentRowsDomain row2 = new DocumentRowsDomain();
        row2.setInvent("Mercury stick");
        row2.setPrice(12);
        row2.setCount(45);
        row1.setRow_id(1);

        ArrayList<DocumentRowsDomain> list = new ArrayList<>();
        list.add(row1);
        list.add(row2);

        domDocument.setGrid(list);

        Track dtoObject = new Track(domDocument);
        EntityObject entityDocument = DtoFactory.toEntity(dtoObject, DocumentEntity.class);

        DocumentEntity docEntity = (DocumentEntity)entityDocument;

        int gridSizeObject = (( docEntity.getGrid() == null ) ? -1 : docEntity.getGrid().size());
        int gridSizeMap    = ((  dtoObject.getAttributes() == null ) ? -1 :  dtoObject.getAttributes().size());


        System.out.println("testToEntity gridSizeObject  " + gridSizeObject);
        System.out.println("testToEntity gridSizeMap  " + gridSizeMap);

        System.out.println("Document > domain: " + domDocument.toString());
        System.out.println("Document < entity: " + docEntity.toString());

        assertTrue(entityDocument instanceof DocumentEntity) ;







    }

    @Test
    public void testToDomain() throws Exception {
        Track dtoObject = new Track(document);
        DomainObject domDocument = DtoFactory.toDomain(dtoObject, DocumentDomain.class);

        DocumentDomain documentDomain = (DocumentDomain)domDocument;

        int gridSizeObject = (( documentDomain.getGrid() == null ) ? -1 : documentDomain.getGrid().size());
        int gridSizeMap    = ((  dtoObject.getAttributes() == null ) ? -1 :  dtoObject.getAttributes().size());



        System.out.println("testToDomain gridSizeObject  " + gridSizeObject);
        System.out.println("testToDomain gridSizeMap  " + gridSizeMap);


        System.out.println("Entity > domain: " + document.toString());
        System.out.println("Document < domain: " + domDocument.toString());


        assertTrue(domDocument instanceof DocumentDomain);




    }
}