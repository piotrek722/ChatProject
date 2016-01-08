package agh.client.gui.mainframe.events;

import agh.router.DefaultEvent;

import java.util.List;

public class DeleteContactsEvent extends DefaultEvent {
    private String userLogin;
    private List<String> contacts; //Or sth

    public DeleteContactsEvent(String userLogin, List<String> contacts) {
        this.userLogin = userLogin;
        this.contacts = contacts;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public List<String> getContacts() {
        return contacts;
    }
}
