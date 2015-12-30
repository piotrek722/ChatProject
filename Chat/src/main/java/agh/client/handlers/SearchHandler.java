package agh.client.handlers;

import agh.client.gui.SearchDialog;
import agh.client.events.SearchEvent;
import agh.router.Handler;
import agh.server.IServer;

public class SearchHandler implements Handler<SearchEvent> {
    private IServer server;
    private SearchDialog searchDialog;

    public SearchHandler(IServer server, SearchDialog searchDialog) {
        this.server = server;
        this.searchDialog = searchDialog;
    }

    @Override
    public void dispatch(SearchEvent message) {
        //get results from server and display on searchDialog (call some method in SearchDialog)
    }
}
