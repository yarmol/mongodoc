package me.jarad.mongo.service;

import me.jarad.mongo.dao.nat.SessionNativeDao;
import me.jarad.mongo.dao.nat.UserNativeDao;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.Spark;

import java.io.StringWriter;

/**
 * Created by vitaly on 04.11.2015.
 */
public class SessionService {

    public static Document getCurrentUser() {
            return new Document("first_name","Vitaly").append("last_name","Yarmolenko");
    }

    public static String getCurrentUserId() {
        return "vitaly";
    }

    public static StringWriter commitRegistration(Request request, Response response,String login, String pass, String email) {

        StringWriter writer = new StringWriter();
        //Template tmpl = Starter.getConf().getTemplate(webPageUrl);
        //Map<String, String> params = new HashMap<>();
        //tmpl.process(params, writer);
        //return writer;

        UserNativeDao user = new UserNativeDao();
        boolean result = user.addUser(login,pass,email);
        if (result) {
            writer = BasicService.simpleLoad("index.html", request, response);
        }
        else {
            Spark.halt(401, "You are not welcome here");
            writer = BasicService.simpleLoad("error.html", request, response);
        }
        return  writer;

    }


    public static boolean isAuth(Request req, Response resp) {
       // return BasicService.checkSession(req, resp);
        Session session = req.session(false);

        //System.out.println("session " + session);
        if (session == null) {
            return  false;
        }
        else {
            return true;
        }

    }


    public static void startSession(String login, Request request) {

        Session session = request.session(true);
        session.attribute("user",login);
        SessionNativeDao sessionDao = new SessionNativeDao();
        sessionDao.addSession(session,login);
        //ServletContext context = session.raw().getServletContext();
        //context.setAttribute(login,session);



    }

    public static boolean checkCredentials(String login, String pass) {


        UserNativeDao user = new UserNativeDao();
        boolean checkingResult = user.checkUserCredentials(login,pass);

        return checkingResult;
    }
}
