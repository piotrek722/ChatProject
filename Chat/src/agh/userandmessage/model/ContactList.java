package agh.userandmessage.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Peter on 2015-11-20.
 * Project name : ChatProject
 */
public class ContactList extends ArrayList<Contact> implements Serializable {

    public ContactList(int initialCapacity) {
        super(initialCapacity);
    }

    public ContactList(Contact... contacts) {
        Collections.addAll(this,contacts);
    }

    public ContactList(Collection<? extends Contact> c) {
        super(c);
    }

    public void add(Contact... contacts) {
        Collections.addAll(this, contacts);
    }

}
