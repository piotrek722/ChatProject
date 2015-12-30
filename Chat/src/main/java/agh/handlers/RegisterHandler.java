package agh.handlers;

import agh.clientgui.LoginDialog;
import agh.clientgui.RegisterDialog;
import agh.events.RegisterEvent;
import agh.router.Channel;
import agh.server.IServer;

public class RegisterHandler implements Channel<RegisterEvent> {
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
        //on fail display error msg on RegisterDialog
        //on success switch to login with success msg
    }
}
