package agh.client.gui.mainframe.handlers;

import agh.client.gui.mainframe.events.ShowAccountSettingsEvent;
import agh.client.gui.accountsettingsdialog.AccountSettingsDialog;
import agh.router.Handler;

public class ShowAccountSettingsHandler implements Handler<ShowAccountSettingsEvent>{
    private AccountSettingsDialog accountSettingsDialog;

    public ShowAccountSettingsHandler(AccountSettingsDialog accountSettingsDialog) {
        this.accountSettingsDialog = accountSettingsDialog;
    }

    @Override
    public void dispatch(ShowAccountSettingsEvent message) {
        accountSettingsDialog.setUser(message.getUser());
        accountSettingsDialog.setVisible(true);
    }
}
