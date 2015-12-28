package agh.eventshandlers;

import agh.router.Event;
import sun.security.util.Password;

public class LoginEvent extends Event {
    private String login;
    private Password password;

    public LoginEvent(String login, Password password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public Password getPassword() {
        return password;
    }
}
