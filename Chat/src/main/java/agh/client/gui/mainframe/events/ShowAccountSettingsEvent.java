package agh.client.gui.mainframe.events;

import agh.router.DefaultEvent;

public class ShowAccountSettingsEvent extends DefaultEvent {
    private String userLogin;

    public ShowAccountSettingsEvent(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return userLogin;
    }
}
