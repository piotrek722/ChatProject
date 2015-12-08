package agh.userandmessage.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Peter on 2015-11-21.
 * Project name : ChatProject
 */

@Entity
@Table(name = "Message")
public class Message implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int messageId;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private Date date;

    @OneToOne
    @JoinColumn(name = "sender")
    private User sender;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Receivers",
            joinColumns = @JoinColumn(name = "messageId"),
            inverseJoinColumns = @JoinColumn(name = "login"))
    private List<User> receivers = new ArrayList<>();

    public Message() {
    }

    public Message(String content, Date date, User sender, List<User> receivers) {
        this.content = content;
        this.date = date;
        this.sender = sender;
        this.receivers = receivers;
    }

    public Message(String content, Date date, User sender, User... receivers) {
        this.content = content;
        this.date = date;
        this.sender = sender;
        this.receivers = Arrays.asList(receivers);
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
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

    public List<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers = receivers;
    }
}

