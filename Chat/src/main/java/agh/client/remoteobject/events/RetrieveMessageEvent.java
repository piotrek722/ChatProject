package agh.client.remoteobject.events;

import agh.model.simple.ClientMessage;
import agh.router.DefaultEvent;

public class RetrieveMessageEvent extends DefaultEvent {
    private ClientMessage message;

    public RetrieveMessageEvent(ClientMessage message) {
        this.message = message;
    }

    public ClientMessage getMessage() {
        return message;
    }
}
