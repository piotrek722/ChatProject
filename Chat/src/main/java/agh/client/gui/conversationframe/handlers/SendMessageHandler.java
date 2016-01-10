package agh.client.gui.conversationframe.handlers;

import agh.client.gui.conversationframe.events.SendMessageEvent;
import agh.router.Handler;
import agh.server.Server;

import java.rmi.RemoteException;

public class SendMessageHandler implements Handler<SendMessageEvent> {
    private Server server;

    public SendMessageHandler(Server server) {
        this.server = server;
    }

    @Override
    public void dispatch(SendMessageEvent message) {
        try {
            server.sendMessage(message.getClientMessage());
        } catch (RemoteException e) {
            //
        }
    }
}
