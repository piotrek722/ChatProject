package agh.client.gui.mainframe.handlers;

import agh.client.gui.mainframe.MainFrame;
import agh.client.gui.mainframe.events.DeleteContactsEvent;
import agh.model.simple.SimplifiedUser;
import agh.router.Handler;
import agh.server.Server;

import java.rmi.RemoteException;

public class DeleteContactsHandler implements Handler<DeleteContactsEvent> {
    private Server server;
    private MainFrame mainFrame;

    public DeleteContactsHandler(Server server, MainFrame mainFrame) {
        this.server = server;
        this.mainFrame = mainFrame;
    }

    @Override
    public void dispatch(DeleteContactsEvent message) {
        for (SimplifiedUser contact : message.getContacts()) {
            try {
                server.deleteContact(message.getUserLogin(), contact.getLogin());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mainFrame.deleteContacts(message.getContacts());
    }
}
