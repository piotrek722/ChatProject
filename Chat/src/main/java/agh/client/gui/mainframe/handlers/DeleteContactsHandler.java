package agh.client.gui.mainframe.handlers;

import agh.client.gui.mainframe.MainFrame;
import agh.client.gui.mainframe.events.DeleteContactsEvent;
import agh.router.Handler;
import agh.server.Server;

public class DeleteContactsHandler implements Handler<DeleteContactsEvent> {
    private Server server;
    private MainFrame mainFrame;

    public DeleteContactsHandler(Server server, MainFrame mainFrame) {
        this.server = server;
        this.mainFrame = mainFrame;
    }

    @Override
    public void dispatch(DeleteContactsEvent message) {
        //Delete contacts from contanctList of User from DB
        mainFrame.deleteContacts(message.getContacts());
    }
}
