package agh.client.gui.conversationframe.handlers;

import agh.client.gui.conversationframe.events.SendMessageEvent;
import agh.router.Handler;
import agh.server.Server;

public class SendMessageHandler implements Handler<SendMessageEvent> {
    private Server server;

    public SendMessageHandler(Server server) {
        this.server = server;
    }

    @Override
    public void dispatch(SendMessageEvent message) {
        //server.sendMessage(message.getContent(), message.getDate(), message.getSender(), message.getReceivers());
    }
}
