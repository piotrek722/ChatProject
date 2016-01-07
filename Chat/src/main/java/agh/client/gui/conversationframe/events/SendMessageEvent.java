package agh.client.gui.conversationframe.events;

import agh.router.DefaultEvent;

import java.util.List;

public class SendMessageEvent extends DefaultEvent {
    private String sender;
    private String message;
    private List<String> receivers;

    public SendMessageEvent(String sender, String message, List<String> receivers) {
        this.sender = sender;
        this.message = message;
        this.receivers = receivers;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getReceivers() {
        return receivers;
    }
}
