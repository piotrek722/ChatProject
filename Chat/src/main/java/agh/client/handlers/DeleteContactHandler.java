package agh.client.handlers;

import agh.client.events.DeleteContactEvent;
import agh.router.Handler;
import agh.server.IServer;

public class DeleteContactHandler implements Handler<DeleteContactEvent> {
    private IServer server;
    //clientGUI?


    public DeleteContactHandler(IServer server) {
        this.server = server;
    }

    @Override
    public void dispatch(DeleteContactEvent message) {
        //Delete contacts from contanctList of User from DB
    }
}
