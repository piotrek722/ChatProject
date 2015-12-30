package agh.client.handlers;

import agh.client.events.ShowAccountSettingsEvent;
import agh.client.gui.AccountSettingsDialog;
import agh.router.Handler;

public class ShowAccountSettingsHandler implements Handler<ShowAccountSettingsEvent>{
    private AccountSettingsDialog accountSettingsDialog;

    public ShowAccountSettingsHandler(AccountSettingsDialog accountSettingsDialog) {
        this.accountSettingsDialog = accountSettingsDialog;
    }

    @Override
    public void dispatch(ShowAccountSettingsEvent message) {
        accountSettingsDialog.setVisible(true);
    }
}
