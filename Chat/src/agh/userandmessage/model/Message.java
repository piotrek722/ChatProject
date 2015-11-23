package agh.userandmessage.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Peter on 2015-11-21.
 * Project name : ChatProject
 */
public class Message implements Serializable{

    private String content;
    private Date date;
    private User sender;
    private List<Contact> receivers;

    public Message(String content, Date date, User sender, List<Contact> receivers) {
        this.content = content;
        this.date = date;
        this.sender = sender;
        this.receivers = receivers;
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

    public List<Contact> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Contact> receivers) {
        this.receivers = receivers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message message = (Message) o;

        if (getContent() != null ? !getContent().equals(message.getContent()) : message.getContent() != null)
            return false;
        if (getDate() != null ? !getDate().equals(message.getDate()) : message.getDate() != null) return false;
        if (getSender() != null ? !getSender().equals(message.getSender()) : message.getSender() != null) return false;
        return !(getReceivers() != null ? !getReceivers().equals(message.getReceivers()) : message.getReceivers() != null);

    }

    @Override
    public int hashCode() {
        int result = getContent() != null ? getContent().hashCode() : 0;
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getSender() != null ? getSender().hashCode() : 0);
        result = 31 * result + (getReceivers() != null ? getReceivers().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", date=" + date +
                ", sender=" + sender +
                ", receivers=" + receivers +
                '}';
    }
}
