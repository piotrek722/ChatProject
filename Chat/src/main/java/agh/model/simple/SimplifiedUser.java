package agh.model.simple;

import java.io.Serializable;

/**
 * Created by Kurtz on 08.01.2016.
 */
public class SimplifiedUser implements Serializable{
    private static final long serialVersionUID = 1L;
    private String login;
    private String firstName;
    private String lastName;

    public SimplifiedUser(String login, String firstName, String lastName) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
        if (o == null || getClass() != o.getClass()) return false;

        SimplifiedUser simplifiedUser = (SimplifiedUser) o;

        return login.equals(simplifiedUser.login);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public String toString() {
        return firstName + lastName + "(" + login + ")";
    }
}
