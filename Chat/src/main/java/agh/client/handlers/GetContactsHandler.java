package agh.client.handlers;

import agh.client.events.GetContactsEvent;
import agh.client.gui.ClientGUI;
import agh.router.Handler;
import agh.server.IServer;

public class GetContactsHandler implements Handler<GetContactsEvent>{
    private IServer server;
    private ClientGUI clientGUI;

    @Override
    public void dispatch(GetContactsEvent message) {
        //get from server contacts for User
        //display in JList of clientGUI
    }
}
