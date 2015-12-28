package agh.guievents;

import agh.router.Event;
import sun.security.util.Password;

public class RegisterEvent extends Event {
    private String login;
    private String fname;
    private String lname;
    private Password password;

    public RegisterEvent(String login, String fname, String lname, Password password) {
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

    public Password getPassword() {
        return password;
    }
}
