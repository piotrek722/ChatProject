package agh.client.handlers;

import agh.client.events.SendMessageEvent;
import agh.router.Handler;
import agh.server.IServer;

public class SendMessageHandler implements Handler<SendMessageEvent> {
    private IServer server;

    public SendMessageHandler(IServer server) {
        this.server = server;
    }

    @Override
    public void dispatch(SendMessageEvent message) {
        //call server method
    }
}
