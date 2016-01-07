package agh.client.gui.accountsettingsdialog.events;

import agh.router.DefaultEvent;

public class ChangePasswordEvent extends DefaultEvent{
    private String currentPassword;
    private String newPassword;

    public ChangePasswordEvent(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
