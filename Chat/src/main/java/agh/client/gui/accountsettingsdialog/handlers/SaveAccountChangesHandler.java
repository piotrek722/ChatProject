package agh.client.gui.accountsettingsdialog.handlers;

import agh.client.gui.accountsettingsdialog.events.SaveAccountChangesEvent;
import agh.client.gui.accountsettingsdialog.AccountSettingsDialog;
import agh.client.gui.mainframe.MainFrame;
import agh.model.simple.SimplifiedUser;
import agh.router.Handler;
import agh.server.Server;

import java.rmi.RemoteException;

public class SaveAccountChangesHandler implements Handler<SaveAccountChangesEvent> {
    private Server server;
    private MainFrame mainFrame;
    private AccountSettingsDialog accountSettingsDialog;

    public SaveAccountChangesHandler(Server server, MainFrame mainFrame, AccountSettingsDialog accountSettingsDialog) {
        this.server = server;
        this.mainFrame = mainFrame;
        this.accountSettingsDialog = accountSettingsDialog;
    }

    @Override
    public void dispatch(SaveAccountChangesEvent message) {
        SimplifiedUser user;
        try {
            user = server.saveAccountChanges(message.getLogin(), message.getFname(), message.getLname());
            if (user != null) {
                mainFrame.setUser(user);
                accountSettingsDialog.setUser(user);
                accountSettingsDialog.displayChangesSavedSuccessfully();
            } else {
                accountSettingsDialog.displaySavingChangesFailed();
            }
        } catch (RemoteException e) {
            //e.printStackTrace();
        }
    }
}
