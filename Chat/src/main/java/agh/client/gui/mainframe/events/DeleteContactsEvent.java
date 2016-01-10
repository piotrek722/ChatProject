package agh.client.gui.mainframe.events;

import agh.model.simple.SimplifiedUser;
import agh.router.DefaultEvent;

import java.util.List;

public class DeleteContactsEvent extends DefaultEvent {
    private String userLogin;
    private List<SimplifiedUser> contacts;

    public DeleteContactsEvent(String userLogin, List<SimplifiedUser> contacts) {
        this.userLogin = userLogin;
        this.contacts = contacts;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public List<SimplifiedUser> getContacts() {
        return contacts;
    }
}
