package me.jarad.mongo.dao;

import me.jarad.mongo.business.pojo.DocumentDomain;
import me.jarad.mongo.business.pojo.DomainObject;
import me.jarad.mongo.dao.gears.StrategyDao;
import me.jarad.mongo.dto.DtoFactory;
import me.jarad.mongo.dto.Track;
import me.jarad.mongo.persistance.DocumentEntity;
import me.jarad.mongo.persistance.EntityObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vitaly on 26.11.2015.
 */
public class FactoryDaoTest {

    @Test
    public void testGetDaoProviderDocument() throws Exception {
        ObjectDao<DocumentEntity> daoDocument = FactoryDao.getDaoProviderDocument(StrategyDao.MORPHIA);
        EntityObject document =   daoDocument.getbyField("number", 256);
        System.out.println(document.toString());

        Track dtoDoc = new Track(document);
        System.out.println("dto = " + dtoDoc.toString());

        DomainObject docDomain = new DocumentDomain();
        docDomain  = DtoFactory.toDomain(dtoDoc,DocumentDomain.class);
        System.out.println(docDomain.toString());

        Track dtoDoc2 = new Track(docDomain);
        System.out.println("dto2 = " + dtoDoc.toString());

        assertTrue(document != null);

    }
}