package agh.userandmessage.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Peter on 2015-11-20.
 * Project name : ChatProject
 */
public class ContactList implements Serializable {

    private ArrayList<Contact> contacts;

    public ContactList() {
        contacts = new ArrayList<>();
    }

    public ContactList(Contact... contacts){
        this();
        for(Contact contact: contacts){
            this.contacts.add(contact);
        }
    }

    public ContactList(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public void removeContact(Contact contact){
        for (Contact cont : contacts) {
            if(contact.equals(cont)){
                contacts.remove(cont);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactList)) return false;

        ContactList that = (ContactList) o;

        return getContacts().equals(that.getContacts());

    }

    @Override
    public int hashCode() {
        return getContacts().hashCode();
    }
}
