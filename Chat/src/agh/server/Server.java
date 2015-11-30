package agh.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import agh.userandmessage.model.ContactList;
import agh.userandmessage.model.Conversation;
import agh.userandmessage.model.User;

public class Server extends UnicastRemoteObject implements ServerInterface {

	private static final long serialVersionUID = 1L;

	protected Server() throws RemoteException {
	}

	public Boolean addContact(User user, String contact) throws RemoteException {
		
		/* if contact not exist in database:
		 *		return false;
		 */
		ContactList contactList = user.getContactList();
		contactList.add(contact);
		user.setContactList(contactList);
		return true;
	}

	public Boolean deleteContact(User user, String contact) throws RemoteException {
		
		/* if contact not exist in database:
		 *		return false;
		 */
		ContactList contactList = user.getContactList();
		contactList.remove(contact);
		user.setContactList(contactList);
		return true;
	}

	public Boolean addUser(User user) throws RemoteException {
		//pass
		return true;
	}

	public Boolean deleteUser(User user) throws RemoteException {
		//pass
		return true;
	}

	public ContactList getContacts(User user) throws RemoteException {
		return user.getContactList();
	}

	public Conversation getConversations(User user, ContactList contacts) throws RemoteException {
		//
		return null;
	}
}
