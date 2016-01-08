package agh.client.gui.mainframe.handlers;

import agh.client.gui.mainframe.events.GetContactsEvent;
import agh.client.gui.mainframe.MainFrame;
import agh.router.Handler;
import agh.server.Server;

public class GetContactsHandler implements Handler<GetContactsEvent>{
    private Server server;
    private MainFrame mainFrame;

    public GetContactsHandler(Server server, MainFrame mainFrame) {
        this.server = server;
        this.mainFrame = mainFrame;
    }

    @Override
    public void dispatch(GetContactsEvent message) {
        //server.getContacts(message.getUserLogin());
        //mainFrame.setContacts();
    }
}
