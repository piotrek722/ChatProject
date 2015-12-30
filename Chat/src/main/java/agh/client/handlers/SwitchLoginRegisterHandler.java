package agh.client.handlers;

import agh.client.gui.LoginDialog;
import agh.client.gui.RegisterDialog;
import agh.client.events.SwitchLoginRegisterEvent;
import agh.router.Handler;

public class SwitchLoginRegisterHandler implements Handler<SwitchLoginRegisterEvent> {
    private LoginDialog loginDialog;
    private RegisterDialog registerDialog;

    public SwitchLoginRegisterHandler(LoginDialog loginDialog, RegisterDialog registerDialog) {
        this.loginDialog = loginDialog;
        this.registerDialog = registerDialog;
    }

    @Override
    public void dispatch(SwitchLoginRegisterEvent message) {
        if (loginDialog.isVisible()) {
            loginDialog.setVisible(false);
            registerDialog.setVisible(true);
        } else {
            registerDialog.setVisible(false);
            loginDialog.setVisible(true);
        }
    }
}
