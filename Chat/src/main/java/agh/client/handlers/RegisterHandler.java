package agh.client.handlers;

import agh.client.gui.LoginDialog;
import agh.client.gui.RegisterDialog;
import agh.client.events.RegisterEvent;
import agh.router.Handler;
import agh.server.IServer;

public class RegisterHandler implements Handler<RegisterEvent> {
    private IServer server;
    private RegisterDialog registerDialog;
    private LoginDialog loginDialog;

    public RegisterHandler(IServer server, RegisterDialog registerDialog, LoginDialog loginDialog) {
        this.server = server;
        this.registerDialog = registerDialog;
        this.loginDialog = loginDialog;
    }

    @Override
    public void dispatch(RegisterEvent message) {
        //on fail display error msg on RegisterDialog Label, clear passField
        //on success switch to login with success msg, clear RegisterDialog
    }
}
