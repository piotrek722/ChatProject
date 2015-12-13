package agh.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import agh.client.ClientInterface;
import agh.userandmessage.model.ContactList;
import agh.userandmessage.model.Message;
import agh.userandmessage.model.User;
import agh.persistence.HibernateUtils;

public class Server extends UnicastRemoteObject implements ServerInterface {
	
	private static final long serialVersionUID = 1L;
	private Map<User, ClientInterface> usersOnline;

	protected Server() throws RemoteException {
		usersOnline = new HashMap<User, ClientInterface>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean registerClient(String login, String password, String name, String lastName) throws RemoteException {
		Boolean isSuccessful = false;
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		String command = "select u from user as u where u.login like :login";
		Query query = session.createQuery(command).setParameter("login", login);
		List<User> found = query.list();
		
		if(found.isEmpty()) {
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
		Boolean isSuccessful = false;
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		String command = "select u from user as u where u.login like :login";
		Query query = session.createQuery(command).setParameter("login", user.getLogin());
		List<User> found = query.list();
		
		transaction.commit();
		session.close();
		
		if(found.size() == 1) {
			session.delete(found.get(0));
			isSuccessful = true;
		}
		
		return isSuccessful;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User login(ClientInterface client, String login, String password) throws RemoteException {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		String command = "select u from user as u where u.login like :login and u.password like :password";
		Query query = session.createQuery(command).setParameter("login", login).setParameter("password", password);
		List<User> found = query.list();
		
		transaction.commit();
		session.close();
		
		if(found.isEmpty()) {
			return null;
		}
		
		User user = found.get(0);
		
		if(user != null) {
			usersOnline.put(user, client);
		}
		
		return user;
	}
	
	@Override
	public Boolean logout(User user) throws RemoteException {		
		if(usersOnline.get(user) != null) {
			usersOnline.remove(user);
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Boolean sendMessage(String content, Date date, User sender, List<String> logins) throws RemoteException {
		if(usersOnline.get(sender) == null) {
			return false;
		}
		
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		String command = "select u from user as u where u.login in elements(:logins)";
		Query query = session.createQuery(command).setParameter("logins", logins);
		List<User> receivers = query.list();
		
		transaction.commit();
		
		for(User receiver : receivers) {
			if(usersOnline.get(receiver) == null) {
				session.close();
				return false;
			}
		}
		
		Message message = new Message(content, date, sender, receivers);
		
		for(User receiver : receivers) {
			usersOnline.get(receiver).retreiveMessage(message);
		}
		
		transaction = session.beginTransaction();
		
		session.persist(message);
		
		transaction.commit();
		session.close();
		
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean addContact(User user, String contact) throws RemoteException {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		String command = "select u from user as u where u.login like :contact";
		Query query = session.createQuery(command).setParameter("contact", contact);
		List<User> found = query.list();
		
		transaction.commit();
		
		if(found.isEmpty()) {
			session.close();
			return false;
		}
		
		ContactList contactList = user.getContactList();
		List<User> userList = contactList.getUserList();
		User contactAsUser = found.get(0);
		
		if(userList.contains(contactAsUser)) {
			session.close();
			return false;
		}
		
		userList.add(contactAsUser);
		contactList.setUserList(userList);
		user.setContactList(contactList);
		
		transaction = session.beginTransaction();
		
		session.saveOrUpdate(user);
		
		transaction.commit();
		session.close();
		return true;
	}
	
	@Override
	public Boolean deleteContact(User user, String contact) throws RemoteException {
		ContactList contactList = user.getContactList();
		List<User> userList = contactList.getUserList();
		User contactAsUser = null;
		
		for(User item : userList) {
			if(item.getLogin().equals(contact)) {
				contactAsUser = item;
				break;
			}
		}
		
		if(contactAsUser == null) {
			return false;
		}
		
		userList.remove(contactAsUser);
		contactList.setUserList(userList);
		user.setContactList(contactList);
		
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		session.saveOrUpdate(user);
		
		transaction.commit();
		session.close();
		return true;
	}

	@Override
	public List<User> getContacts(User user) throws RemoteException {
		ContactList contactList = user.getContactList();
		return contactList.getUserList();
	}
}
