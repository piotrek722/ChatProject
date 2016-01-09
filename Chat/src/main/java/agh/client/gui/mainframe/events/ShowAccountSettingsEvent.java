package agh.client.gui.mainframe.events;

import agh.model.simple.SimplifiedUser;
import agh.router.DefaultEvent;

public class ShowAccountSettingsEvent extends DefaultEvent {
    private SimplifiedUser user;

    public ShowAccountSettingsEvent(SimplifiedUser user) {
        this.user = user;
    }

    public SimplifiedUser getUser() {
        return user;
    }
}
