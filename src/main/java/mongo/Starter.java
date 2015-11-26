

package mongo;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import me.jarad.mongo.dao.*;
import me.jarad.mongo.service.DocumentService;
import me.jarad.mongo.service.SessionService;
import spark.*;
import me.jarad.mongo.service.BasicService;

import java.util.*;


/**
 * Created by vitaly on 30.10.2015.
 */
public class Starter {
    static ConnectionDao connectionDao = new ConnectionDao();
    static Map<String,String> session  = null;
    static Configuration conf = new Configuration();
    static {
        conf.setClassForTemplateLoading(Starter.class, "/");
    }

    public static Configuration getConf() {
        return conf;
    }

    static MongoClient client                   = connectionDao.getClient();
    static MongoDatabase db                     = connectionDao.getDb();




    public static void main(String[] args) {

        Spark.before("/page/*",(request, response) -> {
            boolean authenticated = SessionService.isAuth(request, response);

            if (!authenticated) {
                //Spark.halt(401, "You are not welcome here");
                BasicService.simpleLoad("index.html",request, response);
            }
        });




        Spark.get("/", (req,resp) ->  BasicService.simpleLoad("index.html", req, resp));

        Spark.get("/register", (req,resp) ->  BasicService.simpleLoad("register.html", req, resp));

        Spark.post("/page/docs", (request, response) ->

                {   String login = request.queryParams("login");
                    String pass  = request.queryParams("pass");

                    boolean accessGranted = SessionService.checkCredentials(login, pass);
                    if (accessGranted) {
                        SessionService.startSession(login, request);
                        DocumentService docService = new DocumentService( getConf() , db);
                        return docService.mainPageLoad(request, response);
                    }
                    else {
                        Spark.halt(401, "Wrong credentials");
                        return BasicService.simpleLoad("error.html", request, response);
                    }

                }
        );

        //persist register
        Spark.post("/page/reg", (request, response) -> {
            //Template tmpl = conf.getTemplate("register.html");

           /* Session session = request.session(true);
            int sessionAttrCount = session.attributes().size();
            int reqAttrCount = request.attributes().size();
            int reqParamCount = request.params().size();

            StringWriter writer = new StringWriter();
            writer.write("your data is:\n");
            writer.write("sessionAttrCount = " + sessionAttrCount + "\n");
            writer.write("reqAttrCount = " + reqAttrCount + "\n");
            writer.write("reqParamCount = " + reqParamCount + "\n");
            writer.write("login = " +  + "\n");
            writer.write("pass = " + request.queryParams("pass") + "\n");
            writer.write("email = " + request.queryParams("email") + "\n");
            return writer;*/

            return  SessionService.commitRegistration(request, response, request.queryParams("login"), request.queryParams("pass"), request.queryParams("email"));


        });


        Spark.get("/page/docid/:id", (request, response) -> {

            Map<String, String> reqParams = request.params();
            DocumentService documentService = new DocumentService(getConf(), db);
            return documentService.getDocument(request, response, reqParams.get(":id"));

        });
    }




}
