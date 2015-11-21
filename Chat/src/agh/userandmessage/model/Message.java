package agh.userandmessage.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Peter on 2015-11-21.
 * Project name : ChatProject
 */
public class Message implements Serializable{

    private String content;
    private Date date;
    private User sender;
    private User receiver;

    public Message(String content, Date date, User sender, User receiver) {
        this.content = content;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", date=" + date +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }
}
