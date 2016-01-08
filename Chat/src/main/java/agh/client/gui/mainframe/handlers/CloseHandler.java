package agh.client.gui.mainframe.handlers;

import agh.client.gui.mainframe.events.CloseEvent;
import agh.router.Handler;
import agh.server.Server;

public class CloseHandler implements Handler<CloseEvent> {
    private Server server;

    public CloseHandler(Server server) {
        this.server = server;
    }

    @Override
    public void dispatch(CloseEvent message) {
        //server.unregisterClient(message.getUserLogin());
    }
}
