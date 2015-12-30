package agh.client.handlers;

import agh.client.gui.ClientGUI;
import agh.client.events.ReceiveMessageEvent;
import agh.router.Handler;

public class ReceiveMessageHandler implements Handler<ReceiveMessageEvent> {
    private ClientGUI clientGUI;

    public ReceiveMessageHandler(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }

    @Override
    public void dispatch(ReceiveMessageEvent message) {
        //call some clientGui method which appends a message
        //to a particular conversationTab
    }
}
