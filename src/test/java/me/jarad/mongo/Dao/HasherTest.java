package me.jarad.mongo.Dao;

import static org.junit.Assert.*;

/**
 * Created by vitaly on 04.11.2015.
 */
public class HasherTest {

    @org.junit.Test
    public void testGetRandomString() throws Exception {
        Hasher h = new Hasher();
        System.out.println("random string: "+h.getRandomString());
        String[] result = h.process("ztrbg");
        System.out.println("random 0: "+result[0]);
        System.out.println("random 1: "+result[1]);
        assertSame("Same obj:","ztrbg",result[0]);
    }
}