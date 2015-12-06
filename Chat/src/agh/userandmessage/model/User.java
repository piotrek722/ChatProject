package agh.userandmessage.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="ContactList",
                joinColumns = @JoinColumn(name = "login"),
                inverseJoinColumns = @JoinColumn(name = "contact"))
    private List<User> contactList = new ArrayList<>();

    @ManyToMany(mappedBy = "contactList")
    private List<User> contactOf = new ArrayList<>();

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

    public List<User> getContactList() {
        return contactList;
    }

    public void setContactList(List<User> contactList) {
        this.contactList = contactList;
    }

    public List<User> getContactOf() {
        return contactOf;
    }

    public void setContactOf(List<User> contactOf) {
        this.contactOf = contactOf;
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
}
