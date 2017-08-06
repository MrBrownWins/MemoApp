package me.karimoff.memochat;

import java.util.Date;

/**
 * Created by karimoff on 8/6/17.
 */

//Message class is to control messages in the chat
public class Message {
    private String message;
    private String author;
    private Date created;

    //class constructor
    public Message(String message, String author, Date created) {
        this.message = message;
        this.author = author;
        this.created = created;
    }

    //empty constructor
    public Message() {
    }

    //veriable getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
