package agh;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import agh.userandmessage.model.ContactList;
import agh.userandmessage.model.Conversation;
import agh.userandmessage.model.User;

public class Server extends UnicastRemoteObject implements ServerInterface {

	private static final long serialVersionUID = 1L;

	protected Server() throws RemoteException {
		//pass
	}

	public void addContact(User user, String contact) throws RemoteException {
		//pass		
	}

	public void deleteContact(User user, String contact) throws RemoteException {
		//pass
	}

	public void addUser(User user) throws RemoteException {
		//pass
	}

	public void deleteUser(User user) throws RemoteException {
		//pass
	}

	public ContactList getContacts(User user) throws RemoteException {
		//pass
		return null;
	}

	public Conversation getConversations(User user, ContactList contacts) throws RemoteException {
		//pass
		return null;
	}
}
