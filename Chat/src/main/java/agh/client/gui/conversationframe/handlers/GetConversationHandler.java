package agh.client.gui.conversationframe.handlers;

import agh.client.gui.conversationframe.events.GetConversationEvent;
import agh.model.simple.Conversation;
import agh.router.Handler;
import agh.server.Server;

import java.rmi.RemoteException;

public class GetConversationHandler implements Handler<GetConversationEvent>{
    private Server server;

    public GetConversationHandler(Server server) {
        this.server = server;
    }

    @Override
    public void dispatch(GetConversationEvent message) {
        Conversation conversation;
        try {
            conversation = server.getMessages(message.getSender(), message.getReceivers());
            message.getConversationFrame().displayConversation(conversation);
        } catch (RemoteException e) {
            //
        }
    }
}
