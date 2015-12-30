package agh.client.events;

import agh.router.DefaultEvent;
import sun.security.util.Password;

public class LoginEvent extends DefaultEvent {
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
