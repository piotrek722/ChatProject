package agh.userandmessage.model;

import java.io.Serializable;

/**
 * Created by Peter on 2015-11-20.
 * Project name : ChatProject
 */
public class Contact implements Serializable {

    private String login;

    public Contact(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;

        Contact contact = (Contact) o;

        return !(getLogin() != null ? !getLogin().equals(contact.getLogin()) : contact.getLogin() != null);

    }

    @Override
    public int hashCode() {
        return getLogin() != null ? getLogin().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Contact : " + getLogin();
    }
}
