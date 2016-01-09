package agh.server;

import agh.client.remoteobject.Client;
import agh.model.db.ContactList;
import agh.model.db.Conversation;
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
    public Boolean unregisterClient(User user) throws RemoteException {
        Boolean isSuccessful = false;
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login like :login";
        Query query = session.createQuery(command).setParameter("login", user.getLogin());
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
    public Boolean logout(SimplifiedUser user) throws RemoteException {
        if(usersOnline.get(user.getLogin()) != null) {
            usersOnline.remove(user.getLogin());
            return true;
        }
        return false;
    }

    @Override
    public Boolean sendMessage(String content, Date date, User sender, List<String> logins) throws RemoteException {
        if(usersOnline.get(sender.getLogin()) == null) {
            return false;
        }

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login in (:logins)";
        Query query = session.createQuery(command).setParameterList("logins", logins);
        List<User> receivers = query.list();

        transaction.commit();

        for(User receiver : receivers) {
            if(usersOnline.get(receiver.getLogin()) == null) {
                session.close();
                return false;
            }
        }

        Message message = new Message(content, date, sender, receivers);

        SimplifiedUser csender = new SimplifiedUser(sender.getLogin(),sender.getName(),sender.getLastName());

        List<SimplifiedUser> creceivers = new ArrayList<>();
        for (User u: receivers) {
            creceivers.add(new SimplifiedUser(u.getLogin(),u.getName(),u.getLastName()));
        }

        ClientMessage clientmessage = new ClientMessage(content,date,csender,creceivers);
        usersOnline.get(sender.getLogin()).retrieveMessage(clientmessage);

        for(User receiver : receivers) {
            usersOnline.get(receiver.getLogin()).retrieveMessage(clientmessage);
        }

        transaction = session.beginTransaction();

        session.persist(message);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public Boolean addContact(User user, String contact) throws RemoteException {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

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

        user.getContactList().setContactListId(this.getContacts(user).getContactListId());

        for(User u: this.getContacts(user).getUserList()){
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
    public Boolean deleteContact(User user, String contact) throws RemoteException {
        ContactList contactList = this.getContacts(user);
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

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        session.saveOrUpdate(user);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<SimplifiedUser> getUserContacts(User user) throws RemoteException {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        ContactList contactList = null;
        List<SimplifiedUser> contacts = new ArrayList<>();

        String command = "select cl from User u inner join u.contactList cl where u.login like :login";
        Query query = session.createQuery(command).setParameter("login", user.getLogin());
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
    public ContactList getContacts(User user) throws RemoteException {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        ContactList contactList = null;

        String command = "select cl from User u inner join u.contactList cl where u.login like :login";
        Query query = session.createQuery(command).setParameter("login", user.getLogin());
        if(!query.list().isEmpty()) {
            contactList = (ContactList) query.list().get(0);
        }

        transaction.commit();
        session.close();

        return contactList;
    }

    @Override
    public Conversation getMessages(User user, List<String> selectedContacts) throws RemoteException {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login in (:contacts)";
        Query query = session.createQuery(command).setParameterList("contacts", selectedContacts);
        List<User> selectedUsers = query.list();

        command = "select m from Message m where size(m.receivers) = (:size)" +
                " and m.sender in (select u from User u where u in :participants or u.login like :sender)" +
                " and (select r from m.receivers r) in (select u from User u where u in :participants or u.login like :sender)";

        query = session.createQuery(command).setParameterList("participants", selectedUsers).setParameter("size", selectedUsers.size())
                .setParameter("sender", user.getLogin());
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
}
