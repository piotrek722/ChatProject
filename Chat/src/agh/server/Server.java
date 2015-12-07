package agh.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import agh.client.ClientInterface;

import agh.userandmessage.model.Conversation;
import agh.userandmessage.model.User;

public class Server extends UnicastRemoteObject implements ServerInterface {

	private static final long serialVersionUID = 1L;
	private ArrayList<ClientInterface> clients;

	protected Server() throws RemoteException {
		clients = new ArrayList<ClientInterface>();
	}
	
	public synchronized void registerClient(ClientInterface client, DefaultListModel<User> userOnlineList) throws RemoteException {
		this.clients.add(client);
		userOnlineList.addElement("Client_");
	}

	public synchronized void unregisterClient(ClientInterface client) throws RemoteException {
		this.clients.remove(client);
	}

	public synchronized void broadcastMessage(String message) throws RemoteException {
		for(ClientInterface client : clients) {
			client.retrieveMessage();
		}
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
