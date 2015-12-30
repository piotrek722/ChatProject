package agh.client.events;

import agh.router.DefaultEvent;

import java.util.List;

public class DeleteContactEvent extends DefaultEvent {
    private List<Object> contacts; //Or sth

    public DeleteContactEvent(List<Object> contacts) {
        this.contacts = contacts;
    }

    public List<Object> getContacts() {
        return contacts;
    }
}
