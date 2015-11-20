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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (isOnline() != user.isOnline()) return false;
        if (getLogin() != null ? !getLogin().equals(user.getLogin()) : user.getLogin() != null) return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        return !(getContactList() != null ? !getContactList().equals(user.getContactList()) : user.getContactList() != null);

    }

    @Override
    public int hashCode() {
        int result = getLogin() != null ? getLogin().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getContactList() != null ? getContactList().hashCode() : 0);
        result = 31 * result + (isOnline() ? 1 : 0);
        return result;
    }
}
