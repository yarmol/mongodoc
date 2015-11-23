package me.jarad.mongo.Dao;

import me.jarad.mongo.service.SessionService;
import org.bson.Document;
import spark.Session;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by vitaly on 04.11.2015.
 */
public class SessionDao extends AbstractDao {

    public SessionDao(){
        super();
        setDomainCollection(database.getCollection("sessions"));
    }

    public boolean addSession(Session session, String user ) {
       //  String user      = SessionService.getCurrentUserId();
        String sessionId    = UUID.randomUUID().toString();
        long datestamp      = Calendar.getInstance().getTimeInMillis();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");

        Document sessionDocument = new Document("_id",sessionId);

        sessionDocument.append("user",user);
        sessionDocument.append("date",dateFormat.format(new Date(datestamp)).toString());
        sessionDocument.append("session",session.id());
        sessionDocument.append("creation",dateFormat.format(new Date(session.raw().getCreationTime())).toString());
        add(sessionDocument);
        return true;
    }

}
