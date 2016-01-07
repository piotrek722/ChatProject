package agh.client.gui.accountsettingsdialog.handlers;

import agh.client.gui.accountsettingsdialog.events.SaveAccountChangesEvent;
import agh.client.gui.accountsettingsdialog.AccountSettingsDialog;
import agh.router.Handler;
import agh.server.Server;

public class SaveAccountChangesHandler implements Handler<SaveAccountChangesEvent> {
    private Server server;
    private AccountSettingsDialog accountSettingsDialog;

    public SaveAccountChangesHandler(Server server, AccountSettingsDialog accountSettingsDialog) {
        this.server = server;
        this.accountSettingsDialog = accountSettingsDialog;
    }

    @Override
    public void dispatch(SaveAccountChangesEvent message) {

    }
}
