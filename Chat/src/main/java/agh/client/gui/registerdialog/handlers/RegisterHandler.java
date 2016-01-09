package agh.client.gui.registerdialog.handlers;

import agh.client.gui.logindialog.LoginDialog;
import agh.client.gui.registerdialog.RegisterDialog;
import agh.client.gui.registerdialog.events.RegisterEvent;
import agh.router.Handler;
import agh.server.Server;

import java.rmi.RemoteException;

public class RegisterHandler implements Handler<RegisterEvent> {
    private Server server;
    private RegisterDialog registerDialog;
    private LoginDialog loginDialog;

    public RegisterHandler(Server server, RegisterDialog registerDialog, LoginDialog loginDialog) {
        this.server = server;
        this.registerDialog = registerDialog;
        this.loginDialog = loginDialog;
    }

    @Override
    public void dispatch(RegisterEvent message) {
        try {
            if (server.registerClient(message.getLogin(), message.getPassword(), message.getFname(), message.getLname())) {
                registerDialog.clearDialog();
                registerDialog.setVisible(false);
                loginDialog.registeredSuccessfully();
                loginDialog.setVisible(true);
            } else {
                registerDialog.clearPasswordField();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
