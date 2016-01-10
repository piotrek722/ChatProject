package agh.client.gui.mainframe.events;

import agh.model.simple.SimplifiedUser;
import agh.router.DefaultEvent;

public class LogoutEvent extends DefaultEvent {
    private String userLogin;

    public LogoutEvent(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return userLogin;
    }
}
