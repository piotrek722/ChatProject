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
    private boolean online = false;
    private String name;
    private String lastName;

    public User(String login, String password, String name, String lastName) {
        this.login = login;
        this.password = password;
        contactList = new ContactList();
        this.name = name;
        this.lastName = lastName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return isOnline() == user.isOnline() && !(getLogin() != null ? !getLogin().equals(user.getLogin()) : user.getLogin() != null) && !(getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null) && !(getContactList() != null ? !getContactList().equals(user.getContactList()) : user.getContactList() != null) && !(getName() != null ? !getName().equals(user.getName()) : user.getName() != null) && !(getLastName() != null ? !getLastName().equals(user.getLastName()) : user.getLastName() != null);

    }

    @Override
    public int hashCode() {
        int result = getLogin() != null ? getLogin().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getContactList() != null ? getContactList().hashCode() : 0);
        result = 31 * result + (isOnline() ? 1 : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", online=" + online +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
