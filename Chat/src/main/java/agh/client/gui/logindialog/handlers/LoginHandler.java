package agh.client.gui.logindialog.handlers;

import agh.client.gui.mainframe.MainFrame;
import agh.client.remoteobject.Client;
import agh.client.gui.logindialog.LoginDialog;
import agh.client.gui.logindialog.events.LoginEvent;
import agh.router.Handler;
import agh.server.Server;

public class LoginHandler implements Handler<LoginEvent> {
    private Server server;
    private Client client;
    private LoginDialog loginDialog;
    private MainFrame mainFrame;


    public LoginHandler(Server server, Client client, LoginDialog loginDialog, MainFrame mainFrame) {
        this.server = server;
        this.client = client;
        this.loginDialog = loginDialog;
        this.mainFrame = mainFrame;
    }

    @Override
    public void dispatch(LoginEvent message) {
        /*SimplifiedUser contact;
        contact = server.login(client, message.getLogin(), message.getPassword());

        if (contact != null) {
            loginDialog.clearDialog();
            loginDialog.setVisible(false);
            mainFrame.setUserLogin(contact);
            mainFrame.setVisible(true);
        } else {
            loginDialog.logginginFailed();
            loginDialog.clearPasswordField();
        }*/
    }
}
