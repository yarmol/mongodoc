package me.jarad.mongo.service;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import me.jarad.mongo.dao.nat.DocumentNativeDao;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by vitaly on 04.11.2015.
 */
public class DocumentService {


    private Configuration conf;
    private MongoDatabase db;

    public DocumentService( Configuration conf , MongoDatabase db) {
        this.conf = conf;
        this.db = db;

    }

    public String getDocNumber(String id) {
        Bson filter = Filters.eq("_id", new ObjectId(id));
        Bson selector = Projections.fields(Projections.include("number"), Projections.excludeId());
        Document doc = db.getCollection("documents").find(filter).projection(selector).sort(Sorts.ascending("number")).first();
        return ((Integer)doc.get("number")).toString();
    }

    public ArrayList<Document> getGridDoc(String id) {
        Bson filter = Filters.eq("_id",new ObjectId(id));
        Bson selector = Projections.fields(Projections.include("grid"),Projections.excludeId());
        Document doc = db.getCollection("documents").find(filter).projection(selector).sort(Sorts.ascending("number")).first();
        ArrayList<Document> grid = (ArrayList<Document>)doc.get("grid");

        //System.out.println((String)grid.get(0).toJson());

        return grid;
    }

    public StringWriter getDocument(Request req, Response resp, String id) {
        StringWriter writer = new StringWriter();
        try {
            Template tmpl = conf.getTemplate("doc.ftl");
            Map<String, Object> params = new HashMap<>();
            params.put("doc_number", getDocNumber(id));
            params.put("grid", getGridDoc(id));
            tmpl.process(params, writer);
        }
        catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return writer;
    }


    public StringWriter process (Request req, Response resp, Configuration conf ) {
        StringWriter writer = new StringWriter();
        try {
            Template tmpl = conf.getTemplate("docs.ftl");
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            Map<String,Object> params = new HashMap<>();
            params.put("user", req.session(false).attribute("user"));
            params.put("time", dateFormat.format(new Date(req.session(false).raw().getCreationTime())).toString());
            params.put("table", (LinkedList<Document>)(new DocumentNativeDao()).getDocs());

            tmpl.process(params,writer);
        }
        catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return writer;
    }

    public StringWriter mainPageLoad(Request req, Response resp) {

        //boolean loginResult = BasicService.checkLogin(req, resp);
       // if (loginResult) {
       //
       return this.process(req, resp, conf);
        //} else {
         //   Spark.halt(401, "You are not welcome here");
       // }

    }

    



}
