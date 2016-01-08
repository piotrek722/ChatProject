package agh.client.gui.logindialog.handlers;

import agh.client.gui.mainframe.MainFrame;
import agh.client.remoteobject.Client;
import agh.client.gui.logindialog.LoginDialog;
import agh.client.gui.logindialog.events.LoginEvent;
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
    public void dispatch(LoginEvent message) throws RemoteException {
        //on fail display error msg on LoginDialog Label, clear password
        //on success switch to MainFrame, clear LoginDialog

        server.login(client, message.getLogin(), message.getPassword()); //if successful? else if failed?

        //need String error msg back from server
        mainFrame.setUserLogin(message.getLogin());
    }
}
