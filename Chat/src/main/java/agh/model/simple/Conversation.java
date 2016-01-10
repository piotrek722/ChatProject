package agh.model.simple;

import agh.model.simple.ClientMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 2015-11-21.
 * Project name : ChatProject
 */
public class Conversation implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<ClientMessage> messages;

    public Conversation() {
        messages = new ArrayList<>();
    }

    public List<ClientMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ClientMessage> messages) {
        this.messages = messages;
    }
}
