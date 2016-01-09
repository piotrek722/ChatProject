package agh.server;

import agh.client.remoteobject.Client;
import agh.model.db.ContactList;
import agh.model.db.Conversation;
import agh.model.db.User;
import agh.model.simple.SimplifiedUser;

import java.util.Date;
import java.util.List;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote, Serializable {
    Boolean registerClient(String login, String password, String name, String lastName) throws RemoteException;
    Boolean unregisterClient(User user) throws RemoteException;
    SimplifiedUser login(Client client, String login, String password) throws RemoteException;
    Boolean logout(User user) throws RemoteException;
    Boolean sendMessage(String content, Date date, User sender, List<String> logins) throws RemoteException;
    Boolean addContact(User user, String contact) throws RemoteException;
    Boolean deleteContact(User user, String contact) throws RemoteException;
    ContactList getContacts(User user) throws RemoteException;
    List<SimplifiedUser> getUserContacts(User user) throws RemoteException;
    Conversation getMessages(User user, List<String> selectedContacts) throws RemoteException;
    List<String> getUsersOnline() throws  RemoteException;
    List<SimplifiedUser> getAllUsers() throws RemoteException;
    List<SimplifiedUser> findUser(String login, String name, String lastName) throws RemoteException;
}
