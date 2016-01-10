package agh.client.gui.searchdialog.handlers;

import agh.client.gui.searchdialog.events.AddContactEvent;
import agh.client.gui.mainframe.MainFrame;
import agh.router.Handler;
import agh.server.Server;

import java.rmi.RemoteException;

public class AddContactHandler implements Handler<AddContactEvent> {
    private Server server;
    private MainFrame mainFrame;

    public AddContactHandler(Server server, MainFrame mainFrame) {
        this.server = server;
        this.mainFrame = mainFrame;
    }

    @Override
    public void dispatch(AddContactEvent message) {
        try {
            if(server.addContact(message.getUserLogin(), message.getUserToAdd().getLogin())) {
                mainFrame.addContact(message.getUserToAdd());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
