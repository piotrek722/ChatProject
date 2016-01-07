package agh.client.remoteobject.handlers;

import agh.client.gui.mainframe.MainFrame;
import agh.client.remoteobject.events.ReceiveMessageEvent;
import agh.router.Handler;

public class ReceiveMessageHandler implements Handler<ReceiveMessageEvent> {
    private MainFrame mainFrame;

    public ReceiveMessageHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void dispatch(ReceiveMessageEvent message) {
        //call some clientGui method which appends a message
        //to a particular conversationTab
    }
}
