package me.jarad.mongo.dao.gears;


import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by vitaly on 04.11.2015.
 */
public class Hasher {
    private static int seed = 10;
    private static Random rnd = new Random(seed);


    public String getRandomString() {
        String result =  null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int symbol = rnd.nextInt(256);
            sb.append(Character.valueOf((char)symbol).toString());
        }

        return sb.toString();
    }

    public String[] process(String pass) {
        String[] returnedValue = new String[2];
        try {
            returnedValue[1] = getRandomString();
            String passwordView = pass + ","+returnedValue[1];
            System.out.println(passwordView);
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(pass.getBytes());
            byte[] hashedValue = new String(digest.digest(),"UTF-8").getBytes();
            BASE64Encoder encoder = new BASE64Encoder();
            returnedValue[0] = encoder.encode(hashedValue);

            return returnedValue;
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return returnedValue;


    }
}
