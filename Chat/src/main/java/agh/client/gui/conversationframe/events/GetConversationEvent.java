package agh.client.gui.conversationframe.events;

import agh.client.gui.conversationframe.ConversationFrame;
import agh.router.DefaultEvent;

import java.util.List;

public class GetConversationEvent extends DefaultEvent {
    private String sender;
    private List<String> receivers;
    private ConversationFrame conversationFrame;

    public GetConversationEvent(String sender, List<String> receivers, ConversationFrame conversationFrame) {
        this.sender = sender;
        this.receivers = receivers;
        this.conversationFrame = conversationFrame;
    }

    public String getSender() {
        return sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public ConversationFrame getConversationFrame() {
        return conversationFrame;
    }
}
