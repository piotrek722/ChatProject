package agh.model.simple;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Kurtz on 08.01.2016.
 */
public class ClientMessage {
    private String content;
    private Date date;
    private Contact sender;
    private List<Contact> receivers;
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");

    public ClientMessage(String content, Date date, Contact sender, List<Contact> receivers) {
        this.content = content;
        this.date = date;
        this.sender = sender;
        this.receivers = receivers;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public Contact getSender() {
        return sender;
    }

    public List<Contact> getReceivers() {
        return receivers;
    }

    @Override
    public String toString() {
        return simpleDateFormat.format(date) + " " + sender + " > " + content;
    }
}
