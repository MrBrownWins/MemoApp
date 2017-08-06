package me.karimoff.memochat;

/**
 * Created by karimoff on 8/6/17.
 */

//User class is to control user information
public class User {
    private String email;
    private String uid;

    //empty constructor
    public User() {
    }

    // class constructor
    public User(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }

    //class getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
