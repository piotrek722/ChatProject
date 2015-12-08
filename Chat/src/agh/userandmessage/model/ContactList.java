package agh.userandmessage.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Peter on 2015-11-20.
 * Project name : ChatProject
 */

@Entity
@Table(name = "ContactList")
public class ContactList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int contactListId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "User_Contact",
            joinColumns = @JoinColumn(name = "contactListId"),
            inverseJoinColumns = @JoinColumn(name = "login")
    )
    private List<User> userList = new ArrayList<>();

    public ContactList() {
    }

    public void addContact(User... contacts){
        Collections.addAll(userList,contacts);
    }

    public int getContactListId() {
        return contactListId;
    }

    public void setContactListId(int contactListId) {
        this.contactListId = contactListId;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}