package agh.client.handlers;

import agh.client.events.GetContactsEvent;
import agh.router.Handler;
import agh.server.IServer;

public class GetConversationHandler implements Handler<GetContactsEvent>{
    private IServer server;

    public GetConversationHandler(IServer server) {
        this.server = server;
    }

    @Override
    public void dispatch(GetContactsEvent message) {
        //server.getContacts
        //converseWindow.display(Msg)
    }
}
