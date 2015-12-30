package agh.handlers;

import agh.clientgui.SearchDialog;
import agh.events.SearchEvent;
import agh.router.Channel;
import agh.server.IServer;

public class SearchHandler implements Channel<SearchEvent>{
    private IServer server;
    private SearchDialog searchDialog;

    public SearchHandler(IServer server, SearchDialog searchDialog) {
        this.server = server;
        this.searchDialog = searchDialog;
    }

    @Override
    public void dispatch(SearchEvent message) {
        //get results from server and display on searchDialog
    }
}
