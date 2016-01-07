package agh.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Peter on 2015-11-20.
 * Project name : ChatProject
 */

@Entity
@Table(name="User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "contactListId")
    private ContactList contactList;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    public User() {
    }

    public User(String login, String password, String name, String lastName) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.contactList = new ContactList();
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
        return Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getLastName(), user.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword(), getName(), getLastName());
    }
}