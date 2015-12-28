package agh.client;

import agh.eventshandlers.EventDispatcher;
import agh.server.IServer;

public class GUIEventDispatcher extends EventDispatcher {
    private IServer server;

    public GUIEventDispatcher(IServer server) {
        super();
        this.server = server;
    }
}
