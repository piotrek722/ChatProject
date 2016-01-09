package agh.client.gui.mainframe.events;

import agh.model.simple.SimplifiedUser;
import agh.router.DefaultEvent;

public class LogoutEvent extends DefaultEvent {
    private SimplifiedUser user;

    public LogoutEvent(SimplifiedUser user) {
        this.user = user;
    }

    public SimplifiedUser getUser() {
        return user;
    }
}
