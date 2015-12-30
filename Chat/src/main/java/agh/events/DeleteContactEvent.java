package agh.events;

import agh.router.Event;

import java.util.List;

public class DeleteContactEvent extends Event {
    private List<Object> contacts; //Or sth

    public DeleteContactEvent(List<Object> contacts) {
        this.contacts = contacts;
    }

    public List<Object> getContacts() {
        return contacts;
    }
}
