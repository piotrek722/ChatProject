package agh.client.handlers;

import agh.client.events.SwitchRegisterToLoginEvent;
import agh.client.gui.LoginDialog;
import agh.client.gui.RegisterDialog;
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
