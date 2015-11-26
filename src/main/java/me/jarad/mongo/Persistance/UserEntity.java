package me.jarad.mongo.persistance;

import org.mongodb.morphia.annotations.*;

/**
 * Created by vitaly on 25.11.2015.
 */
@Entity
public class UserEntity implements EntityObject{

    @Id
    private String user;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    private String email;
    private String password;
    private String salt;

}
