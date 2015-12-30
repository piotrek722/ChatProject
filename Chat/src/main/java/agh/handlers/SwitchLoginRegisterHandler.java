package agh.handlers;

import agh.clientgui.LoginDialog;
import agh.clientgui.RegisterDialog;
import agh.events.SwitchLoginRegisterEvent;
import agh.router.Channel;

public class SwitchLoginRegisterHandler implements Channel<SwitchLoginRegisterEvent>{
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
