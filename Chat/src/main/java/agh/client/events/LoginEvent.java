package agh.client.events;

import agh.router.DefaultEvent;

public class LoginEvent extends DefaultEvent {
    private String login;
    private String password;

    public LoginEvent(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
