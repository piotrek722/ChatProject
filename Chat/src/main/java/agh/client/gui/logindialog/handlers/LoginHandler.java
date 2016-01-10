package agh.client.gui.logindialog.handlers;

import agh.client.gui.mainframe.MainFrame;
import agh.client.remoteobject.Client;
import agh.client.gui.logindialog.LoginDialog;
import agh.client.gui.logindialog.events.LoginEvent;
import agh.model.simple.SimplifiedUser;
import agh.router.Handler;
import agh.server.Server;

import java.rmi.RemoteException;

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
        SimplifiedUser contact;
        try {
            contact = server.login(client, message.getLogin(), message.getPassword());
            if (contact != null) {
                loginDialog.clearDialog();
                loginDialog.setVisible(false);
                mainFrame.setUser(contact);
                mainFrame.setVisible(true);
            } else {
                loginDialog.loggingInFailed();
                loginDialog.clearPasswordField();
            }
        } catch (RemoteException e) {
            loginDialog.loggingInFailed(e.toString());
        }

    }
}
