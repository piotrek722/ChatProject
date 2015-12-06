package agh.userandmessage.model;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Peter on 2015-11-21.
 * Project name : ChatProject
 */
public class Conversation  implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int conversationId;

    private List<Message> messages = new ArrayList<>();

    public Conversation() {
    }

    public Conversation(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public Conversation(Message... messages) {
        Collections.addAll(this.messages,messages);
    }

    public void add(Message... messages){
        Collections.addAll(this.messages,messages);
    }


}
