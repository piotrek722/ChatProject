package agh.client.handlers;

import agh.client.gui.ClientGUI;
import agh.client.gui.LoginDialog;
import agh.client.events.LogoutEvent;
import agh.router.Handler;

public class LogoutHandler implements Handler<LogoutEvent> {
    private ClientGUI clientGUI;
    private LoginDialog loginDialog;

    public LogoutHandler(ClientGUI clientGUI, LoginDialog loginDialog) {
        this.clientGUI = clientGUI;
        this.loginDialog = loginDialog;
    }

    @Override
    public void dispatch(LogoutEvent message) {
        //Clear & close clientGUI
        //Show LoginDialog
    }
}
