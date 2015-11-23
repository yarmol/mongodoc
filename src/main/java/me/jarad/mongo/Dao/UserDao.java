package me.jarad.mongo.Dao;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaly on 30.10.2015.
 */
public class UserDao extends AbstractDao {

    public UserDao() {
       super();
       setDomainCollection(database.getCollection("users"));
        try {
            if (getDomainCollection().equals("")) {
                DaoException e = new DaoException("Collection handle is empty");
                throw e;
            }
        }
        catch (DaoException e) {
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
        ArrayList<String> userNamesList = UserDao.docListToStringList(getUsers(), "_id");
        return  userNamesList.contains(userName);
    }

    public boolean checkEmailExist(String email) {
        ArrayList<String> emailsList = UserDao.docListToStringList(getUsers(), "email");
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
                add(getDomainCollection(), addUserProcess(userName, password, email));
            }
            else {
                throw new DaoException("Can not add user");
            }
        }
        catch (DaoException e) {
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
            add(getDomainCollection(), userDocument );
        }

        return true;
    }

}
