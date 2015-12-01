package me.jarad.mongo.view;

import me.jarad.mongo.dto.Track;
import me.jarad.mongo.service.BasementObject;

/**
 * Created by vitaly on 30.11.2015.
 */
public class Views {

    public static String toStringView(BasementObject obj) {
        Track dto = new Track(obj);
        return obj.getClass().getSimpleName() + " - " + dto.toString();
    }

}
