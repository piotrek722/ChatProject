package agh.client.handlers;

import agh.client.IClient;
import agh.client.gui.ClientGUI;
import agh.client.gui.LoginDialog;
import agh.client.events.LoginEvent;
import agh.router.Handler;
import agh.server.IServer;

public class LoginHandler implements Handler<LoginEvent> {
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
        //on fail display error msg on LoginDialog Label, clear password
        //on success switch to ClientGUI, clear LoginDialog
    }
}
