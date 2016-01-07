package agh.client.gui.searchdialog.handlers;

import agh.client.gui.searchdialog.events.AddContactEvent;
import agh.client.gui.mainframe.MainFrame;
import agh.router.Handler;
import agh.server.Server;

public class AddContactHandler implements Handler<AddContactEvent> {
    private Server server;
    private MainFrame mainFrame;

    public AddContactHandler(Server server, MainFrame mainFrame) {
        this.server = server;
        this.mainFrame = mainFrame;
    }

    @Override
    public void dispatch(AddContactEvent message) {
        //server addcontact
        //clientgui addcontact
    }
}
