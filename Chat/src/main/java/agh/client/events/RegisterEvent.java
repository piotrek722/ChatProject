package agh.client.events;

import agh.router.DefaultEvent;

public class RegisterEvent extends DefaultEvent {
    private String login;
    private String fname;
    private String lname;
    private String password;

    public RegisterEvent(String login, String fname, String lname, String password) {
        this.login = login;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
