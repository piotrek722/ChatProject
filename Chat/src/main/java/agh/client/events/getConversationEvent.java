package agh.client.events;

import agh.client.gui.ConversationWindow;
import agh.router.DefaultEvent;

import java.util.List;

public class GetConversationEvent extends DefaultEvent {
    private String sender;
    private List<String> receivers;
    private ConversationWindow conversationWindow;

    public GetConversationEvent(String sender, List<String> receivers, ConversationWindow conversationWindow) {
        this.sender = sender;
        this.receivers = receivers;
        this.conversationWindow = conversationWindow;
    }

    public String getSender() {
        return sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public ConversationWindow getConversationWindow() {
        return conversationWindow;
    }
}
