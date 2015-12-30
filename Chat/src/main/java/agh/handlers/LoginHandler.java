package agh.handlers;

import agh.client.IClient;
import agh.clientgui.ClientGUI;
import agh.clientgui.LoginDialog;
import agh.events.LoginEvent;
import agh.router.Channel;
import agh.server.IServer;

public class LoginHandler implements Channel<LoginEvent> {
    private IServer server;
    private IClient client;
    private LoginDialog loginDialog;
    private ClientGUI clientGUI;


    public LoginHandler(IServer server, IClient client, LoginDialog loginDialog, ClientGUI clientGUI) {
        this.server = server;
        this.client = client;
        this.loginDialog = loginDialog;
        this.clientGUI = clientGUI;
    }

    @Override
    public void dispatch(LoginEvent message) {
        //on fail display error msg on LoginDialog
        //on success switch to ClientGUI
    }
}
