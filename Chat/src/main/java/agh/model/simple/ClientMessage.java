package agh.model.simple;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Kurtz on 08.01.2016.
 */
public class ClientMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String content;
    private Date date;
    private SimplifiedUser sender;
    private List<SimplifiedUser> receivers;
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");

    public ClientMessage(String content, Date date, SimplifiedUser sender, List<SimplifiedUser> receivers) {
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

    public SimplifiedUser getSender() {
        return sender;
    }

    public List<SimplifiedUser> getReceivers() {
        return receivers;
    }

    @Override
    public String toString() {
        return simpleDateFormat.format(date) + " " + sender.getLogin() + " > " + content;
    }
}
