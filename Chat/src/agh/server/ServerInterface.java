package agh.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import agh.userandmessage.model.Conversation;
import agh.userandmessage.model.User;

public interface ServerInterface extends Remote {
	void registerClient(ClientInterface client) throws RemoteException;
	void broadcastMessage(String message) throws RemoteException;
	void unregisterClient(IClient client) throws RemoteException;
	User logIn(String login, String password) throws RemoteException;
	Boolean addContact(User user, String contact) throws RemoteException;
	Boolean deleteContact(User user, String contact) throws RemoteException;
	Boolean addUser(User user) throws RemoteException;
	Boolean deleteUser(User user) throws RemoteException;
	List<User> getContacts(User user) throws RemoteException;
	Conversation getConversations(User user, List<User> contacts) throws RemoteException;
}
