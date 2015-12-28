package agh.eventshandlers;

import agh.router.Channel;
import agh.server.IServer;

public class RegisterHandler implements Channel<RegisterEvent> {
    private IServer server;

    public RegisterHandler(IServer server) {
        this.server = server;
    }

    @Override
    public void dispatch(RegisterEvent message) {

    }
}
