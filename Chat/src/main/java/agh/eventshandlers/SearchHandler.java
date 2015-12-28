package agh.eventshandlers;

import agh.router.Channel;
import agh.server.IServer;

public class SearchHandler implements Channel<SearchEvent>{
    private IServer server;

    public SearchHandler(IServer server) {
        this.server = server;
    }

    @Override
    public void dispatch(SearchEvent message) {

    }
}
