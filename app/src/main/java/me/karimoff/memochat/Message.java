package me.karimoff.memochat;

import java.util.Date;

/**
 * Created by karimoff on 8/6/17.
 */

public class Message {
    private String message;
    private String author;
    private Date created;

    public Message(String message, String author, Date created) {
        this.message = message;
        this.author = author;
        this.created = created;
    }
}
