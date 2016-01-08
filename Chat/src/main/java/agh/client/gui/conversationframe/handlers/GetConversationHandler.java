package agh.client.gui.conversationframe.handlers;

import agh.client.gui.conversationframe.events.GetConversationEvent;
import agh.client.gui.mainframe.events.GetContactsEvent;
import agh.router.Handler;
import agh.server.Server;

import java.rmi.RemoteException;

public class GetConversationHandler implements Handler<GetConversationEvent>{
    private Server server;

    public GetConversationHandler(Server server) {
        this.server = server;
    }

    @Override
    public void dispatch(GetConversationEvent message) throws RemoteException {
        //server.getMessages(message.getSender(), message.getReceivers());
        //converseWindow.displayConversation(List<Msg>)
    }
}
