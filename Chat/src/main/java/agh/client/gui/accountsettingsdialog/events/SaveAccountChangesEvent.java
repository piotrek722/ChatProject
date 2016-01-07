package agh.client.gui.accountsettingsdialog.events;

import agh.router.DefaultEvent;

public class SaveAccountChangesEvent extends DefaultEvent {
    private String login;
    private String fname;
    private String lname;

    public SaveAccountChangesEvent(String login, String fname, String lname) {
        this.login = login;
        this.fname = fname;
        this.lname = lname;
    }

    public String getLogin() {
        return login;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }
}
