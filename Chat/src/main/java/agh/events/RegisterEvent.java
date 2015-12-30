package agh.events;

import agh.router.Event;

public class RegisterEvent extends Event {
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
