package agh.server;

import agh.client.remoteobject.Client;
import agh.model.db.ContactList;
import agh.model.simple.Conversation;
import agh.model.db.Message;
import agh.model.db.User;
import agh.model.simple.ClientMessage;
import agh.model.simple.SimplifiedUser;
import agh.persistance.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultServer extends UnicastRemoteObject implements Server {
    private static final long serialVersionUID = 1L;
    private Map<String, Client> usersOnline;

    public DefaultServer() throws RemoteException {
        super();
        usersOnline = new HashMap<>();
        HibernateUtils.setNewConfiguration("Chat/src/main/resources/hibernate.cfg.xml");
    }

    public DefaultServer(String configurationPath) throws RemoteException {
        super();
        usersOnline = new HashMap<>();
        HibernateUtils.setNewConfiguration(configurationPath);
    }

    @Override
    public Boolean registerClient(String login, String password, String name, String lastName) throws RemoteException {
        Boolean isSuccessful = false;
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login like :login";
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

    @Override
    public Boolean unregisterClient(String login) throws RemoteException {
        Boolean isSuccessful = false;
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login like :login";
        Query query = session.createQuery(command).setParameter("login", login);
        List<User> found = query.list();

        if(!found.isEmpty()) {
            session.delete(found.get(0));
            isSuccessful = true;
        }

        transaction.commit();

        session.close();

        return isSuccessful;
    }

    @Override
    public SimplifiedUser login(Client client, String login, String password) throws RemoteException {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login like :login and u.password like :password";
        Query query = session.createQuery(command).setParameter("login", login).setParameter("password", password);
        List<User> found = query.list();

        transaction.commit();
        session.close();

        if(found.isEmpty()) {
            return null;
        }

        User user = found.get(0);

        if(user != null) {
            usersOnline.put(user.getLogin(), client);
        }

        return new SimplifiedUser(user.getLogin(), user.getName(), user.getLastName());
    }

    @Override
    public Boolean logout(String login) throws RemoteException {
        if(usersOnline.get(login) != null) {
            usersOnline.remove(login);
            return true;
        }
        return false;
    }

    @Override
    public Boolean sendMessage(ClientMessage clientmessage) throws RemoteException {
        if(usersOnline.get(clientmessage.getSender().getLogin()) == null) {
            return false;
        }

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String commandUser = "select u from User u where u.login like :login";
        Query queryUser = session.createQuery(commandUser).setParameter("login", clientmessage.getSender().getLogin());
        List<User> found = queryUser.list();

        if (found.isEmpty()) {
            session.close();
            return false;
        }

        User sender = found.get(0);

        String command = "select u from User u where u.login in (:logins)";
        List<String> logins = clientmessage.getReceivers().stream().map(e -> e.getLogin()).collect(Collectors.toList());
        Query query = session.createQuery(command).setParameterList("logins", logins);
        List<User> receivers = query.list();

        transaction.commit();

        Message message = new Message(clientmessage.getContent(), clientmessage.getDate(), sender, receivers);

        SimplifiedUser csender = new SimplifiedUser(sender.getLogin(), sender.getName(), sender.getLastName());

        List<SimplifiedUser> simplifiedReceivers = new ArrayList<>();
        for (User u: receivers) {
            simplifiedReceivers.add(new SimplifiedUser(u.getLogin(),u.getName(),u.getLastName()));
        }

        usersOnline.get(csender.getLogin()).retrieveMessage(clientmessage);

        for(User receiver : receivers) {
            if (usersOnline.get(receiver.getLogin()) != null) {
                usersOnline.get(receiver.getLogin()).retrieveMessage(clientmessage);
            }
        }

        transaction = session.beginTransaction();

        session.persist(message);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public Boolean addContact(String login, String contact) throws RemoteException {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String commandUser = "select u from User u where u.login like :login";
        Query queryUser = session.createQuery(commandUser).setParameter("login", login);
        List<User> found = queryUser.list();

        if (found.isEmpty()) {
            session.close();
            return false;
        }

        User user = found.get(0);

        User contactToAdd = null;
        String command = "select u from User u where u.login like :contact";
        Query query = session.createQuery(command).setParameter("contact", contact);
        if(!query.list().isEmpty()) {
            contactToAdd = (User) query.list().get(0);
        }

        if(contactToAdd == null) {
            session.close();
            return false;
        }

        user.getContactList().setContactListId(this.getContacts(login).getContactListId());

        for(User u: this.getContacts(login).getUserList()){
            if(u.getLogin().equals(contactToAdd.getLogin())){
                session.close();
                return true;
            }
        }

        user.getContactList().getUserList().add(contactToAdd);

        session.saveOrUpdate(user);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Boolean deleteContact(String login, String contact) throws RemoteException {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String commandUser = "select u from User u where u.login like :login";
        Query queryUser = session.createQuery(commandUser).setParameter("login", login);
        List<User> found = queryUser.list();

        if (found.isEmpty()) {
            session.close();
            return  false;
        }

        User user = found.get(0);

        ContactList contactList = this.getContacts(login);
        List<User> userList = contactList.getUserList();
        User contactAsUser = null;

        for(User item : userList) {
            if(item.getLogin().equals(contact)) {
                contactAsUser = item;
                break;
            }
        }

        if(contactAsUser == null) {
            return true;
        }

        userList.remove(contactAsUser);
        contactList.setUserList(userList);
        user.setContactList(contactList);

        session.saveOrUpdate(user);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public List<SimplifiedUser> getUserContacts(String login) throws RemoteException {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        ContactList contactList = null;
        List<SimplifiedUser> contacts = new ArrayList<>();

        String command = "select cl from User u inner join u.contactList cl where u.login like :login";
        Query query = session.createQuery(command).setParameter("login", login);
        if(!query.list().isEmpty()) {
            contactList = (ContactList) query.list().get(0);

            for (User u : contactList.getUserList()) {
                contacts.add(new SimplifiedUser(u.getLogin(),u.getName(),u.getLastName()));
            }
        }

        transaction.commit();
        session.close();

        return contacts;
    }

    @Override
    public ContactList getContacts(String login) throws RemoteException {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        ContactList contactList = null;

        String command = "select cl from User u inner join u.contactList cl where u.login like :login";
        Query query = session.createQuery(command).setParameter("login", login);
        if(!query.list().isEmpty()) {
            contactList = (ContactList) query.list().get(0);
        }

        transaction.commit();
        session.close();

        return contactList;
    }

    @Override
    public Conversation getMessages(String login, List<String> selectedContacts) throws RemoteException {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login in (:contacts)";
        Query query = session.createQuery(command).setParameterList("contacts", selectedContacts);
        List<User> selectedUsers = query.list();

        command = "select m from Message m where size(m.receivers) = (:size)" +
                " and m.sender in (select u from User u where u in :participants or u.login like :sender)" +
                " and (select r from m.receivers r) in (select u from User u where u in :participants or u.login like :sender)";

        query = session.createQuery(command).setParameterList("participants", selectedUsers).setParameter("size", selectedUsers.size())
                .setParameter("sender", login);
        List<Message> messages = query.list();

        transaction.commit();
        session.close();

        List<ClientMessage> clientmessages = new ArrayList<>();
        List<SimplifiedUser> creceivers = new ArrayList<>();

        for (Message m: messages) {
            SimplifiedUser c = new SimplifiedUser(m.getSender().getLogin(),m.getSender().getName(),m.getSender().getLastName());
            for (User u : m.getReceivers()) {
                creceivers.add(new SimplifiedUser(u.getLogin(),u.getName(),u.getLastName()));
            }
            clientmessages.add(new ClientMessage(m.getContent(),m.getDate(),c,creceivers));
        }

        Conversation conversation = new Conversation();
        conversation.setMessages(clientmessages);
        return conversation;
    }

    @Override
    public List<String> getUsersOnline() throws RemoteException {

        List<String> users = new ArrayList<>();
        users.addAll(usersOnline.keySet());

        return users;
    }

    @Override
    public List<SimplifiedUser> getAllUsers() throws RemoteException {

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("select u from User u");
        List<User> users = query.list();

        transaction.commit();
        session.close();

        List<SimplifiedUser> contacts = new ArrayList<>();
        for (User u : users) {
            contacts.add(new SimplifiedUser(u.getLogin(),u.getName(),u.getLastName()));
        }

        return contacts;
    }

    @Override
    public List<SimplifiedUser> findUser(String login, String name, String lastName) throws RemoteException {

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(User.class);

        if(!login.equals("")){
            criteria.add(Restrictions.like("login",login));
        }

        if(!name.equals("")){
            criteria.add(Restrictions.like("name",name));
        }

        if(!lastName.equals("")){
            criteria.add(Restrictions.like("lastName", lastName));
        }

        List<User> users = criteria.list();

        List<SimplifiedUser> contacts = new ArrayList<>();

        for (User u: users) {
            contacts.add(new SimplifiedUser(u.getLogin(),u.getName(),u.getLastName()));
        }

        transaction.commit();
        session.close();

        return contacts;
    }

    @Override
    public SimplifiedUser saveAccountChanges(String login, String name, String lastName) throws RemoteException {
        SimplifiedUser simplifiedUser = null;
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login like :login";
        Query query = session.createQuery(command).setParameter("login", login);
        List<User> found = query.list();

        if (!found.isEmpty()) {
            User user = found.get(0);
            simplifiedUser = new SimplifiedUser(login, name, lastName);

            if (name != "") {
                user.setName(name);
            }

            if (lastName != "") {
                user.setLastName(lastName);
            }
            session.saveOrUpdate(user);
        }

        transaction.commit();
        session.close();

        return simplifiedUser;
    }

    @Override
    public Boolean changePassword(String login, String oldPassword, String newPassword) throws RemoteException {
        Boolean isSuccessful = false;
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login like :login and u.password like :password";
        Query query = session.createQuery(command).setParameter("login", login).setParameter("password", oldPassword);
        List<User> found = query.list();


        if (!found.isEmpty()) {
            User user = found.get(0);
            user.setPassword(newPassword);
            session.saveOrUpdate(user);
            isSuccessful = true;
        }

        transaction.commit();
        session.close();

        return isSuccessful;
    }
}
