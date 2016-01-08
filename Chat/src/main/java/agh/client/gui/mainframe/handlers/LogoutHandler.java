package agh.client.gui.mainframe.handlers;

import agh.client.gui.mainframe.MainFrame;
import agh.client.gui.logindialog.LoginDialog;
import agh.client.gui.mainframe.events.LogoutEvent;
import agh.router.Handler;
import agh.server.Server;

public class LogoutHandler implements Handler<LogoutEvent> {
    private Server server;
    private MainFrame mainFrame;
    private LoginDialog loginDialog;

    public LogoutHandler(Server server, MainFrame mainFrame, LoginDialog loginDialog) {
        this.server = server;
        this.mainFrame = mainFrame;
        this.loginDialog = loginDialog;
    }

    @Override
    public void dispatch(LogoutEvent message) {
        //server.logout(message.getUserLogin());
        mainFrame.clearFrame();
        mainFrame.setVisible(false);
        loginDialog.setVisible(true);
    }
}
