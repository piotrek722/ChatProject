package agh.client.gui.mainframe.events;

import agh.model.simple.Contact;
import agh.router.DefaultEvent;

public class ShowAccountSettingsEvent extends DefaultEvent {
    private Contact user;

    public ShowAccountSettingsEvent(Contact user) {
        this.user = user;
    }

    public Contact getUser() {
        return user;
    }
}
