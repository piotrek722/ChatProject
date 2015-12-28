package agh.eventshandlers;

import agh.router.Channel;
import agh.server.IServer;

public class LoginHandler implements Channel<LoginEvent> {
    private IServer server;

    public LoginHandler(IServer server) {
        this.server = server;
    }

    @Override
    public void dispatch(LoginEvent message) {

    }
}
