package agh.guievents;

import agh.router.Channel;
import agh.server.IServer;

public class SendMessageHandler implements Channel<SendMessageEvent> {
    private IServer server;

    public SendMessageHandler(IServer server) {
        this.server = server;
    }

    @Override
    public void dispatch(SendMessageEvent message) {

    }
}
