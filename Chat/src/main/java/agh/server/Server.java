package agh.server;

import agh.client.remoteobject.Client;
import agh.model.db.ContactList;
import agh.model.simple.ClientMessage;
import agh.model.simple.Conversation;
import agh.model.simple.SimplifiedUser;

import java.util.List;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote, Serializable {
    Boolean registerClient(String login, String password, String name, String lastName) throws RemoteException;
    Boolean unregisterClient(String login) throws RemoteException;
    SimplifiedUser login(Client client, String login, String password) throws RemoteException;
    Boolean logout(String login) throws RemoteException;
    Boolean sendMessage(ClientMessage clientmessage) throws RemoteException;
    Boolean addContact(String login, String contact) throws RemoteException;
    Boolean deleteContact(String login, String contact) throws RemoteException;
    List<SimplifiedUser> getUserContacts(String login) throws RemoteException;
    ContactList getContacts(String login) throws RemoteException;
    Conversation getMessages(String login, List<String> selectedContacts) throws RemoteException;
    List<String> getUsersOnline() throws  RemoteException;
    List<SimplifiedUser> getAllUsers() throws RemoteException;
    List<SimplifiedUser> findUser(String login, String name, String lastName) throws RemoteException;
    SimplifiedUser saveAccountChanges(String login, String name, String lastName) throws RemoteException;
    Boolean changePassword(String login, String oldPassword, String newPassword) throws RemoteException;
}
