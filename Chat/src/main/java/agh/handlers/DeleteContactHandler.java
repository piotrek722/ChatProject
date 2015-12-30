package agh.handlers;

import agh.events.DeleteContactEvent;
import agh.router.Channel;
import agh.server.IServer;

public class DeleteContactHandler implements Channel<DeleteContactEvent>{
    private IServer server;
    //clientGUI?


    public DeleteContactHandler(IServer server) {
        this.server = server;
    }

    @Override
    public void dispatch(DeleteContactEvent message) {

    }
}
