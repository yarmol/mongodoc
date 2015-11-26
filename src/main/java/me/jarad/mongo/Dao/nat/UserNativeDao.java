package me.jarad.mongo.dao.nat;

import me.jarad.mongo.dao.ExceptionDao;
import me.jarad.mongo.dao.gears.Hasher;
import me.jarad.mongo.dao.gears.KeyInChecker;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaly on 30.10.2015.
 */
public class UserNativeDao extends AbstractNativeDao {

    public UserNativeDao() {
       super();
       setCollection(database.getCollection("users"));
        try {
            if (getCollection().equals("")) {
                ExceptionDao e = new ExceptionDao("Collection handle is empty");
                throw e;
            }
        }
        catch (ExceptionDao e) {
            e.printStackTrace();
        }
    }


    public List<Document> getUsers() {
        return getList("password");
    }

    public List<Document> getUsersByFilter(String key,String operatorView,String value) {
        return getFilteredList(key,operatorView,value);
    }

    public boolean checkUserCredentials(String userName, String password) {
        if (checkUserExist(userName)) {
            Document user       = getUser(userName);

            Hasher hshFactory   = new Hasher();
            boolean securityCheck = true;//hshFactory.checkPassword(user.getString("password"),user.getString("salt"));
            return securityCheck;
        }
        else
        {
            return false;
        }
    }


    public boolean checkUserExist(String userName) {
        ArrayList<String> userNamesList = docListToStringList(getUsers(), "_id");
        return  userNamesList.contains(userName);
    }

    public boolean checkEmailExist(String email) {
        ArrayList<String> emailsList = docListToStringList(getUsers(), "email");
        return  emailsList.contains(email);
    }


    public Document getUser(String userName) {
        return this.get(userName);
    }

    public Document addUserProcess(String userName, String password, String email) {
        //checking user exist


        Hasher hshFactory = new Hasher();
        String[] hashedString = hshFactory.process(password);
        Document userDocument = new Document("_id",userName).append("email",email).
                    append("password", hashedString[0]).
                    append("salt", hashedString[1]);
        return userDocument;
    }

    public boolean addUser(String userName, String password, String email) {
        //checking user exist

        if (checkUserExist(userName) || checkEmailExist(email))  {
            return false;
        }

        try {
            if (KeyInChecker.check(userName) && KeyInChecker.check(password) && KeyInChecker.check(email)) {
                add(getCollection(), addUserProcess(userName, password, email));
            }
            else {
                throw new ExceptionDao("Can not add user");
            }
        }
        catch (ExceptionDao e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean addUser(String userName, String password, String email, String firstName, String lastName) {
        //checking user exist

        if (checkUserExist(userName) || checkEmailExist(email))  {
            return false;
        }


        if (KeyInChecker.check(userName) && KeyInChecker.check(password)  && KeyInChecker.check(email)) {
            Document userDocument = addUserProcess(userName, password, email);
            userDocument.append("first_name", firstName);
            userDocument.append("last_name", lastName);
            add(getCollection(), userDocument );
        }

        return true;
    }

    @Override
    public Iterable<Document> getIterator() {
        return null;
    }

    @Override
    public List<Document> getList(int limit, int skip) {
        return null;
    }

    @Override
    public List<Document> getList(int limit) {
        return null;
    }

    @Override
    public Document get(Object id) {
        return null;
    }
}
