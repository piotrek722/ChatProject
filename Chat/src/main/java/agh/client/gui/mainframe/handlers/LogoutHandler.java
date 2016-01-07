package agh.client.gui.mainframe.handlers;

import agh.client.gui.mainframe.MainFrame;
import agh.client.gui.logindialog.LoginDialog;
import agh.client.gui.mainframe.events.LogoutEvent;
import agh.router.Handler;

public class LogoutHandler implements Handler<LogoutEvent> {
    private MainFrame mainFrame;
    private LoginDialog loginDialog;

    public LogoutHandler(MainFrame mainFrame, LoginDialog loginDialog) {
        this.mainFrame = mainFrame;
        this.loginDialog = loginDialog;
    }

    @Override
    public void dispatch(LogoutEvent message) {
        mainFrame.clearFrame();
        mainFrame.setVisible(false);
        loginDialog.setVisible(true);
    }
}
