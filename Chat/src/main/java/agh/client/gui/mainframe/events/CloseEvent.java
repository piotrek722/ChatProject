package agh.client.gui.mainframe.events;

import agh.router.DefaultEvent;

public class CloseEvent extends DefaultEvent {
    private String userLogin;

    public CloseEvent(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return userLogin;
    }
}
