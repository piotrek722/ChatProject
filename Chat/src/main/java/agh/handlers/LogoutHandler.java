package agh.handlers;

import agh.clientgui.ClientGUI;
import agh.clientgui.LoginDialog;
import agh.events.LogoutEvent;
import agh.router.Channel;

public class LogoutHandler implements Channel<LogoutEvent>{
    private ClientGUI clientGUI;
    private LoginDialog loginDialog;

    @Override
    public void dispatch(LogoutEvent message) {
        //Clear & close clientGUI
        //Show LoginDialog
    }
}
