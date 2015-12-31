package agh.client.handlers;

import agh.client.events.SaveAccountChangesEvent;
import agh.client.gui.AccountSettingsDialog;
import agh.router.Handler;
import agh.server.IServer;

public class SaveAccountChangesHandler implements Handler<SaveAccountChangesEvent> {
    private IServer server;
    private AccountSettingsDialog accountSettingsDialog;

    public SaveAccountChangesHandler(IServer server, AccountSettingsDialog accountSettingsDialog) {
        this.server = server;
        this.accountSettingsDialog = accountSettingsDialog;
    }

    @Override
    public void dispatch(SaveAccountChangesEvent message) {

    }
}
