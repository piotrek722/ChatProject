package agh.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import agh.userandmessage.model.Conversation;
import agh.userandmessage.model.User;

public class Server extends UnicastRemoteObject implements ServerInterface {

	private static final long serialVersionUID = 1L;

	protected Server() throws RemoteException {
	}

	@Override
	public User logIn(String login, String password) throws RemoteException {
		return null;
	}

	public Boolean addContact(User user, String contact) throws RemoteException {
		
		/* if contact not exist in database:
		 *		return false;
		 */

		return true;
	}

	public Boolean deleteContact(User user, String contact) throws RemoteException {
		
		/* if contact not exist in database:
		 *		return false;
		 */

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

	public List<User> getContacts(User user) throws RemoteException {
		//pass
		return user.getContactList();
	}

	public Conversation getConversations(User user, List<User> contacts) throws RemoteException {
		//pass
		return null;
	}
}
