package agh.client.gui.conversationframe.events;

import agh.router.DefaultEvent;

import java.util.Date;
import java.util.List;

public class SendMessageEvent extends DefaultEvent {
    private String content;
    private Date date;
    private String sender;
    private List<String> receivers;

    public SendMessageEvent(String content, Date date, String sender, List<String> receivers) {
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

    public String getSender() {
        return sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }
}
