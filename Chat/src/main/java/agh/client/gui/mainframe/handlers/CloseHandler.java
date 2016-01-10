package agh.client.gui.mainframe.handlers;

import agh.client.gui.mainframe.events.CloseEvent;
import agh.router.Handler;
import agh.server.Server;

import java.rmi.RemoteException;

public class CloseHandler implements Handler<CloseEvent> {
    private Server server;

    public CloseHandler(Server server) {
        this.server = server;
    }

    @Override
    public void dispatch(CloseEvent message) {
        try {
            server.logout(message.getUserLogin());
        } catch (RemoteException e) {
            //
        }
    }
}
