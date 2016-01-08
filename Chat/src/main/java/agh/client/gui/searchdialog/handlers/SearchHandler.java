package agh.client.gui.searchdialog.handlers;

import agh.client.gui.searchdialog.SearchDialog;
import agh.client.gui.searchdialog.events.SearchEvent;
import agh.model.Message;
import agh.router.Handler;
import agh.server.Server;

import java.rmi.RemoteException;

public class SearchHandler implements Handler<SearchEvent> {
    private Server server;
    private SearchDialog searchDialog;

    public SearchHandler(Server server, SearchDialog searchDialog) {
        this.server = server;
        this.searchDialog = searchDialog;
    }

    @Override
    public void dispatch(SearchEvent message) throws RemoteException {
        server.findUser(message.getLogin(), message.getFname(), message.getLname());
        //Display result to JTable;
    }
}
