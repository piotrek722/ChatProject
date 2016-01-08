package agh.model.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 2015-11-21.
 * Project name : ChatProject
 */
public class Conversation  implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<Message> messages = new ArrayList<>();

    public Conversation() {
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
