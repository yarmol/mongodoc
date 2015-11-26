package me.jarad.mongo.dao.morphia;

import me.jarad.mongo.persistance.DocumentEntity;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 * Created by vitaly on 25.11.2015.
 */
public class DocumentMorphiaDao extends AbstractMorphiaDao<DocumentEntity> {


    public DocumentMorphiaDao() {
        super();
        setCollectionName("documents");
        setClassDescriptor(DocumentEntity.class);
    }


}
