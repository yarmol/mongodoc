
package me.jarad.mongo.service;

import freemarker.template.TemplateException;
import me.jarad.mongo.*;
import freemarker.template.Template;
import mongo.Starter;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vitaly on 30.10.2015.
 */
public class BasicService {

    public static boolean checkSession(Request req, Response resp) {
        Session session = req.session(false);
        return (session != null);
    }

    public static StringWriter simpleLoad(String webPageUrl, Request req, Response resp)  {
         StringWriter writer = new StringWriter();
         try {
              Template tmpl = Starter.getConf().getTemplate(webPageUrl);
              Map<String, String> params = new HashMap<>();
              tmpl.process(params, writer);
          }
          catch (IOException | TemplateException e) {
               resp.redirect("error.html");
          }

          return writer;


    }


    public static boolean checkLogin(Request req, Response resp) {
        String userLogin = req.queryParams("login");
        String userPass = req.queryParams("pass");

        return true;
    }
}
