package agh.userandmessage.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Peter on 2015-11-20.
 * Project name : ChatProject
 */
public class ContactList extends ArrayList<String> implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public ContactList(int initialCapacity) {
        super(initialCapacity);
    }

    public ContactList(Collection<? extends String> c) {
        super(c);
    }

    public ContactList() {
    }

    public ContactList(String... contacts){
        Collections.addAll(this,contacts);
    }

    public void add(String... contacts){
        Collections.addAll(this,contacts);
    }

}
