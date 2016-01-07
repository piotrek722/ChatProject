package agh.client.gui.conversationframe.handlers;

import agh.client.gui.mainframe.events.GetContactsEvent;
import agh.router.Handler;
import agh.server.Server;

public class GetConversationHandler implements Handler<GetContactsEvent>{
    private Server server;

    public GetConversationHandler(Server server) {
        this.server = server;
    }

    @Override
    public void dispatch(GetContactsEvent message) {
        //server.getConversation
        //converseWindow.displayConversation(List<Msg>)
    }
}
