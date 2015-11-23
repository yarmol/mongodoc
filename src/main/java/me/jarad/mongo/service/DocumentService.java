package me.jarad.mongo.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import me.jarad.mongo.Dao.DocumentDao;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by vitaly on 04.11.2015.
 */
public class DocumentService {

    public StringWriter process (Request req, Response resp, Configuration conf ) {
        StringWriter writer = new StringWriter();
        try {
            Template tmpl = conf.getTemplate("docs.ftl");
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            Map<String,Object> params = new HashMap<>();
            params.put("user", req.session(false).attribute("user"));
            params.put("time", dateFormat.format(new Date(req.session(false).raw().getCreationTime())).toString());
            params.put("table", (LinkedList<Document>)(new DocumentDao()).getDocs());

            tmpl.process(params,writer);
        }
        catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return writer;
    }

    public StringWriter mainPageLoad(Request req, Response resp, Configuration conf) {

        //boolean loginResult = BasicService.checkLogin(req, resp);
       // if (loginResult) {
       //
       return this.process(req, resp, conf);
        //} else {
         //   Spark.halt(401, "You are not welcome here");
       // }

    }
}
