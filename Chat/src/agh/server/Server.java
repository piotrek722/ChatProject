package agh.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import agh.client.ClientInterface;
import agh.userandmessage.model.Conversation;
import agh.userandmessage.model.User;
import agh.persistence.HibernateUtils;

public class Server extends UnicastRemoteObject implements ServerInterface {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<ClientInterface> clients;			// replacing soon

	protected Server() throws RemoteException {
		clients = new ArrayList<ClientInterface>();		// not for long
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean registerClient(String login, String password, String name, String lastName) throws RemoteException {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		Boolean isSuccessful = false;
		
		Query query = session.createQuery("select u from User as u where u.login like :login").setParameter("login", login);
		List<User> result = query.list();
		
		if(result.isEmpty()) {
			User user = new User(login, password, name, lastName);
			session.persist(user);
			isSuccessful = true;
		}
		
		transaction.commit();
		session.close();
		return isSuccessful;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean unregisterClient(User user) throws RemoteException {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		Boolean isSuccessful = false;
		
		Query query = session.createQuery("select u from User as u where u.login like :login").setParameter("login", user.getLogin());
		List<User> result = query.list();
		
		if(!result.isEmpty()) {
			session.delete(result.get(0));
			isSuccessful = true;
		}
		
		transaction.commit();
		session.close();
		return isSuccessful;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User logIn(String login, String password) throws RemoteException {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		User user = null;
		
		Query query = session.createQuery("select u from User as u where u.login like :login and u.password like :password").setParameter("login", login).setParameter("password", password);
		List<User> result = query.list();
		
		if(!result.isEmpty()) {
			user = result.get(0);
		}
		
		transaction.commit();
		session.close();
		return user;
	}

	@Override
	public void sendMessage(String message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}	

	@Override
	public Boolean addContact(User user, String contact) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteContact(User user, String contact) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addUser(User user) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteUser(User user) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getContacts(User user) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Conversation getConversations(User user, List<User> contacts) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}
