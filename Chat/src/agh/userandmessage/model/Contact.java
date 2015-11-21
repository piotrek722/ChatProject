package agh.userandmessage.model;

import java.io.Serializable;

/**
 * Created by Peter on 2015-11-20.
 * Project name : ChatProject
 */
public class Contact implements Serializable {

    private String login;
    private String name;
    private String lastName;

    public Contact(String login, String name, String lastName) {
        this.login = login;
        this.name = name;
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        if (!(o instanceof Contact)) return false;

        Contact contact = (Contact) o;

        return !(getLogin() != null ? !getLogin().equals(contact.getLogin()) : contact.getLogin() != null) && !(getName() != null ? !getName().equals(contact.getName()) : contact.getName() != null) && !(getLastName() != null ? !getLastName().equals(contact.getLastName()) : contact.getLastName() != null);

    }

    @Override
    public int hashCode() {
        int result = getLogin() != null ? getLogin().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
