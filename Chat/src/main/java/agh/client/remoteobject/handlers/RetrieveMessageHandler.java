package agh.client.remoteobject.handlers;

import agh.client.gui.mainframe.MainFrame;
import agh.client.remoteobject.events.RetrieveMessageEvent;
import agh.router.Handler;

public class RetrieveMessageHandler implements Handler<RetrieveMessageEvent> {
    private MainFrame mainFrame;

    public RetrieveMessageHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void dispatch(RetrieveMessageEvent message) {
        //call some clientGui method which appends a message
        //to a particular conversationTab
    }
}
