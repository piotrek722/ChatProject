package agh.client.gui.logindialog.handlers;

import agh.client.gui.logindialog.LoginDialog;
import agh.client.gui.registerdialog.RegisterDialog;
import agh.client.gui.logindialog.events.SwitchLoginToRegisterEvent;
import agh.router.Handler;

public class SwitchLoginToRegisterHandler implements Handler<SwitchLoginToRegisterEvent> {
    private LoginDialog loginDialog;
    private RegisterDialog registerDialog;

    public SwitchLoginToRegisterHandler(LoginDialog loginDialog, RegisterDialog registerDialog) {
        this.loginDialog = loginDialog;
        this.registerDialog = registerDialog;
    }

    @Override
    public void dispatch(SwitchLoginToRegisterEvent message) {
        loginDialog.setVisible(false);
        registerDialog.setVisible(true);
    }
}
