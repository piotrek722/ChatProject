package agh;

import java.rmi.Remote;
import java.rmi.RemoteException;

import agh.userandmessage.model.ContactList;
import agh.userandmessage.model.Conversation;
import agh.userandmessage.model.User;

public interface ServerInterface extends Remote {
	void addContact(User user, String contact) throws RemoteException;
	void deleteContact(User user, String contact) throws RemoteException;
	void addUser(User user) throws RemoteException;
	void deleteUser(User user) throws RemoteException;
	ContactList getContacts(User user) throws RemoteException;
	Conversation getConversations(User user, ContactList contacts) throws RemoteException;
}
