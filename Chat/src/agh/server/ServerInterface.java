package agh.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import agh.client.ClientInterface;
import agh.userandmessage.model.User;

public interface ServerInterface extends Remote {
	Boolean registerClient(String login, String password, String name, String lastName) throws RemoteException;
	Boolean unregisterClient(User user) throws RemoteException;
	User login(ClientInterface client, String login, String password) throws RemoteException;
	Boolean logout(User user) throws RemoteException;
	Boolean sendMessage(String content, Date date, User sender, List<String> logins) throws RemoteException;
	Boolean addContact(User user, String contact) throws RemoteException;
	Boolean deleteContact(User user, String contact) throws RemoteException;
	List<User> getContacts(User user) throws RemoteException;
}
