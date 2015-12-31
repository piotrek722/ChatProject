package agh.client.events;

import agh.router.DefaultEvent;

public class AddContactEvent extends DefaultEvent {
    private String nick;

    public AddContactEvent(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }
}
