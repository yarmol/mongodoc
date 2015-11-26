package me.jarad.mongo.dao.morphia;

import me.jarad.mongo.persistance.DocumentEntity;
import me.jarad.mongo.persistance.UserEntity;

/**
 * Created by vitaly on 25.11.2015.
 */
public class UserMorphiaDao  extends AbstractMorphiaDao<DocumentEntity>{

    public UserMorphiaDao() {
        super();
        setCollectionName("users");
        setClassDescriptor(UserEntity.class);
    }

}
