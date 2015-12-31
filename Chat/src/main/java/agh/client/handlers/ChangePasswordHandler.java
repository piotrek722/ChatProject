package agh.client.handlers;

import agh.client.events.ChangePasswordEvent;
import agh.client.gui.AccountSettingsDialog;
import agh.router.Handler;
import agh.server.IServer;

public class ChangePasswordHandler implements Handler<ChangePasswordEvent> {
    private IServer server;
    private AccountSettingsDialog accountSettingsDialog;

    public ChangePasswordHandler(IServer server, AccountSettingsDialog accountSettingsDialog) {
        this.server = server;
        this.accountSettingsDialog = accountSettingsDialog;
    }

    @Override
    public void dispatch(ChangePasswordEvent message) {

    }
}
