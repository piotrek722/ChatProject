package agh.client.gui.mainframe.events;

import agh.model.simple.SimplifiedUserList;
import agh.router.DefaultEvent;


public class DeleteContactsEvent extends DefaultEvent {
    private String userLogin;
    private SimplifiedUserList contacts;

    public DeleteContactsEvent(String userLogin, SimplifiedUserList contacts) {
        this.userLogin = userLogin;
        this.contacts = contacts;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public SimplifiedUserList getContacts() {
        return contacts;
    }
}
