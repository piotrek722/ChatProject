package agh.userandmessage.model;

import java.io.Serializable;

/**
 * Created by Peter on 2015-11-20.
 * Project name : ChatProject
 */
public class User implements Serializable {

    private String login;
    private String password;
    private ContactList contactList;
    private boolean online;

    public User() {
        this("","");
    }

    public User(String login, String password) {
        this(login, password, null);
    }

    public User(String login, String password, ContactList contactList) {
        this.login = login;
        this.password = password;
        this.contactList = contactList;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ContactList getContactList() {
        return contactList;
    }

    public void setContactList(ContactList contactList) {
        this.contactList = contactList;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "User : " + getLogin() + " online : " + isOnline();
    }
}
