package agh.client.gui.searchdialog.events;

import agh.model.simple.SimplifiedUser;
import agh.router.DefaultEvent;

public class AddContactEvent extends DefaultEvent {
    private String userLogin;
    private SimplifiedUser userToAdd;

    public AddContactEvent(String userLogin, SimplifiedUser userToAdd) {
        this.userLogin = userLogin;
        this.userToAdd = userToAdd;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public SimplifiedUser getUserToAdd() {
        return userToAdd;
    }
}
