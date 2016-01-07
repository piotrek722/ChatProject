package agh.client.gui.accountsettingsdialog.handlers;

import agh.client.gui.accountsettingsdialog.events.ChangePasswordEvent;
import agh.client.gui.accountsettingsdialog.AccountSettingsDialog;
import agh.router.Handler;
import agh.server.Server;

public class ChangePasswordHandler implements Handler<ChangePasswordEvent> {
    private Server server;
    private AccountSettingsDialog accountSettingsDialog;

    public ChangePasswordHandler(Server server, AccountSettingsDialog accountSettingsDialog) {
        this.server = server;
        this.accountSettingsDialog = accountSettingsDialog;
    }

    @Override
    public void dispatch(ChangePasswordEvent message) {

    }
}
