package me.karimoff.memochat;

import java.util.Date;
import java.util.List;

/**
 * Created by karimoff on 8/6/17.
 */

public class Chat {
    public Date created;
    public List<Message> messages;


    public Chat(Date created, List<Message> messages) {
        this.created = created;
        this.messages = messages;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
