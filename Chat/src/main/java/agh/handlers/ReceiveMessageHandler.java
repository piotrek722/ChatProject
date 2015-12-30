package agh.handlers;

import agh.clientgui.ClientGUI;
import agh.events.ReceiveMessageEvent;
import agh.router.Channel;

public class ReceiveMessageHandler implements Channel<ReceiveMessageEvent> {
    private ClientGUI clientGUI;

    public ReceiveMessageHandler(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }

    @Override
    public void dispatch(ReceiveMessageEvent message) {

    }
}
