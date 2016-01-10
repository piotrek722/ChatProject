package agh.client.gui.accountsettingsdialog.events;

import agh.router.DefaultEvent;

public class ChangePasswordEvent extends DefaultEvent{
    private String login;
    private String currentPassword;
    private String newPassword;

    public ChangePasswordEvent(String login, String currentPassword, String newPassword) {
        this.login = login;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getLogin() {
        return login;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
