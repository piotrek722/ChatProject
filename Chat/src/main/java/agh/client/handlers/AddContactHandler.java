package agh.client.handlers;

import agh.client.events.AddContactEvent;
import agh.client.gui.ClientGUI;
import agh.router.Handler;
import agh.server.IServer;

public class AddContactHandler implements Handler<AddContactEvent> {
    private IServer server;
    private ClientGUI clientGUI;

    public AddContactHandler(IServer server, ClientGUI clientGUI) {
        this.server = server;
        this.clientGUI = clientGUI;
    }

    @Override
    public void dispatch(AddContactEvent message) {
        //server addcontact
        //clientgui addcontact
    }
}
