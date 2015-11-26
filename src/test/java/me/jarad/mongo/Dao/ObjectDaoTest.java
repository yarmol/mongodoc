package me.jarad.mongo.dao;

import me.jarad.mongo.dao.morphia.DocumentMorphiaDao;
import me.jarad.mongo.dto.Track;
import me.jarad.mongo.persistance.DocumentEntity;
import me.jarad.mongo.persistance.DocumentRowsEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by vitaly on 25.11.2015.
 */
public class ObjectDaoTest {
    DocumentMorphiaDao daoDoc = new DocumentMorphiaDao();
    @Test
    public void testAdd() throws Exception {
        DocumentEntity doc = new DocumentEntity();
        doc.setKind("Outgoing act");
        doc.setAuthor("John Doe");
        doc.setNumber(257);

        DocumentRowsEntity table = new DocumentRowsEntity();
        table.setRow_id(0);
        table.setInvent("Steel rat");
        table.setCount(3);
        table.setPrice(45.64);

        ArrayList<DocumentRowsEntity> tableList = new ArrayList<>();
        tableList.add(table);


        doc.setGrid(tableList);

        daoDoc.add(doc);

        Track trackDTO = new Track(doc);

        System.out.println("Track = ");
        System.out.println(trackDTO.toString());

        assertTrue(doc.getGrid().size() > 0);

    }

    @Test
    public void testGetIterator() throws Exception {
        Iterable<DocumentEntity> docsIterator = daoDoc.getIterator();
        Iterator iterator = docsIterator.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            DocumentEntity doc = (DocumentEntity)iterator.next();
            i++;
        }

        assertTrue(i>0);

    }

    @Test
    public void testGetList() throws Exception {

    }

    @Test
    public void testGetList1() throws Exception {

    }

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testGet1() throws Exception {

    }
}