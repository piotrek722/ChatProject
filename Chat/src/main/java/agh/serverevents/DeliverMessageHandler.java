package agh.serverevents;

import agh.clientgui.ClientGUI;
import agh.router.Channel;

public class DeliverMessageHandler implements Channel<DeliverMessageEvent> {
    private ClientGUI clientGUI;

    public DeliverMessageHandler(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }

    @Override
    public void dispatch(DeliverMessageEvent message) {

    }
}
