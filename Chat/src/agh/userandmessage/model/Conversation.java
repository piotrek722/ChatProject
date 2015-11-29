package agh.userandmessage.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Peter on 2015-11-21.
 * Project name : ChatProject
 */
public class Conversation extends ArrayList<Message> implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public Conversation(int initialCapacity) {
        super(initialCapacity);
    }

    public Conversation() {
    }

    public Conversation(Message... messages) {
        Collections.addAll(this,messages);
    }

    public Conversation(Collection<? extends Message> c) {
        super(c);
    }

    public void add(Message... messages){
        Collections.addAll(this,messages);
    }
}
