package agh.client.gui.searchdialog.events;

import agh.router.DefaultEvent;

public class AddContactEvent extends DefaultEvent {
    private String userLogin;
    private String loginToAdd;

    public AddContactEvent(String userLogin, String loginToAdd) {
        this.userLogin = userLogin;
        this.loginToAdd = loginToAdd;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getLoginToAdd() {
        return loginToAdd;
    }
}
