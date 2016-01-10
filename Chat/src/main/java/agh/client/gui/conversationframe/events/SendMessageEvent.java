package agh.client.gui.conversationframe.events;

import agh.model.simple.ClientMessage;
import agh.router.DefaultEvent;

import java.util.Date;
import java.util.List;

public class SendMessageEvent extends DefaultEvent {
    private ClientMessage clientMessage;

    public SendMessageEvent(ClientMessage clientMessage) {
        this.clientMessage = clientMessage;
    }

    public ClientMessage getClientMessage() {
        return clientMessage;
    }
}
