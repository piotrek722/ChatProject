package agh.server;

import agh.eventshandlers.EventDispatcher;
import agh.clientgui.ClientGUI;

public class ServerEventDispatcher extends EventDispatcher {
    ClientGUI clientGUI;

    public ServerEventDispatcher(ClientGUI clientGUI) {
        super();
        this.clientGUI = clientGUI;
    }
}
