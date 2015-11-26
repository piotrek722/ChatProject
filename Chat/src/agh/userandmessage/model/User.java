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
    private String name;
    private String lastName;

    public User(String login, String password, String name, String lastName) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        contactList = new ContactList();
    }

    public User(String login, String password, boolean online, String name, String lastName) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        contactList = new ContactList();
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

        if (getLogin() != null ? !getLogin().equals(user.getLogin()) : user.getLogin() != null) return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        if (getContactList() != null ? !getContactList().equals(user.getContactList()) : user.getContactList() != null)
            return false;
        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) return false;
        return !(getLastName() != null ? !getLastName().equals(user.getLastName()) : user.getLastName() != null);

    }

    @Override
    public int hashCode() {
        int result = getLogin() != null ? getLogin().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getContactList() != null ? getContactList().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        return result;
    }

}
