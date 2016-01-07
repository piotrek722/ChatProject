package agh.client.gui.mainframe.events;

import agh.router.DefaultEvent;

import java.util.List;

public class DeleteContactsEvent extends DefaultEvent {
    private List<String> contacts; //Or sth

    public DeleteContactsEvent(List<String> contacts) {
        this.contacts = contacts;
    }

    public List<String> getContacts() {
        return contacts;
    }
}
