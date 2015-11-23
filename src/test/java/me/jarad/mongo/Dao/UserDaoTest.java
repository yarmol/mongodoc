package me.jarad.mongo.Dao;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by vitaly on 23.11.2015.
 */
public class UserDaoTest {
    UserDao user = new UserDao();

    @Test
    public void testAddUser() throws Exception {

        boolean result = user.addUser("vitaly", "ztrbg", "yarmol@yandex.com");
        System.out.println("testAddUser "  + result);
        assertTrue(result);
    }

    @Test
    public void testGetUsers() throws Exception {
        List<Document> listOfDocs = user.getUsers();
        System.out.println("List");
        for (Document doc : listOfDocs) {
            System.out.println(doc.toJson());
        }
        assertTrue(listOfDocs.size() > 0);

    }
/*
    @Test
    public void testGetUsersByFilter() throws Exception {

    }
*/
    @Test
    public void testCheckUserCredentials() throws Exception {
        boolean result = user.checkUserCredentials("vitaly", "ztrbg");
        System.out.println("credentials "  + result);
        assertTrue(result);
    }
/*
    @Test
    public void testCheckUserExist() throws Exception {

    }

    @Test
    public void testCheckEmailExist() throws Exception {

    }

    @Test
    public void testGetUser() throws Exception {

    }*/

    @Test
    public void testAddUserProcess() throws Exception {
        Document doc = user.addUserProcess("vitaly", "ztrbg", "yarmol@yandex.com");
        System.out.println("doc:");
        System.out.println(doc.toJson());
        assertTrue(doc.size() > 0);
    }
/*
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }*/
}


