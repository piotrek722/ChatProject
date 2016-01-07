package agh.client.gui.registerdialog.handlers;

import agh.client.gui.registerdialog.events.SwitchRegisterToLoginEvent;
import agh.client.gui.logindialog.LoginDialog;
import agh.client.gui.registerdialog.RegisterDialog;
import agh.router.Handler;

public class SwitchRegisterToLoginHandler implements Handler<SwitchRegisterToLoginEvent> {
    private RegisterDialog registerDialog;
    private LoginDialog loginDialog;

    public SwitchRegisterToLoginHandler(RegisterDialog registerDialog, LoginDialog loginDialog) {
        this.registerDialog = registerDialog;
        this.loginDialog = loginDialog;
    }

    @Override
    public void dispatch(SwitchRegisterToLoginEvent message) {
        registerDialog.setVisible(false);
        loginDialog.setVisible(true);
    }
}
