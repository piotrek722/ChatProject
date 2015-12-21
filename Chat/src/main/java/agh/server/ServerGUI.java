package agh.server;

import agh.client.ClientInterface;
import agh.persistance.HibernateUtils;
import agh.userandmessage.model.ContactList;
import agh.userandmessage.model.Conversation;
import agh.userandmessage.model.Message;
import agh.userandmessage.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.*;

public class ServerGUI extends JFrame {
    private JPanel mainPanel;
    private JButton sendButton;
    private JTextField textField;
    private JList usersJList;
    private DefaultListModel<String> usersOnline;
    private JTextArea textArea;

    private Server server;

    private static final Logger LOGGER = Logger.getLogger("ServerLogger");
    private static final int SERVER_WIDTH = 600;
    private static final int SERVER_HEIGHT = 400;

    public ServerGUI() throws RemoteException, UnsupportedLookAndFeelException {
        super("Chat Server");
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        setContentPane(mainPanel);
        setSize(SERVER_WIDTH, SERVER_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        server = new Server();

        sendButton.addActionListener(e -> onSend());

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    onSend();
                }
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //User&Messages
            //Server shutdown
        }));

        setVisible(true);
    }

    private void createUIComponents() {
        usersOnline = new DefaultListModel<>();
        usersJList = new JList(usersOnline);
    }

    private void onSend() {
        String servermsg = "SERVER> " + textField.getText();
        textField.setText("");
        textArea.append(servermsg + "\n");

        //User&Messages
        //Broadcast or sth
    }

    public Server getServer() {
        return server;
    }


    /////SERVER
    public class Server extends UnicastRemoteObject implements ServerInterface {

        private static final long serialVersionUID = 1L;
        private Map<String, ClientInterface> usersOnline;

        protected Server() throws RemoteException {
            usersOnline = new HashMap<>();
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

            if(found.size() == 1) {
                session.delete(found.get(0));
                isSuccessful = true;
            }
            transaction.commit();

            session.close();

            return isSuccessful;
        }

        @Override
        public User login(ClientInterface client, String login, String password) throws RemoteException {
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

            return user;
        }

        @Override
        public Boolean logout(User user) throws RemoteException {
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
            usersOnline.get(sender.getLogin()).retreiveMessage(message);

            for(User receiver : receivers) {
                usersOnline.get(receiver.getLogin()).retreiveMessage(message);
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

            user.getContactList().addContact(contactToAdd);

            session.merge(user);

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

            command = "select m from Message m inner join m.receivers where" +
                    " m.sender.id like :sender and size(m.receivers) <> (:size)" +
                    "and exists (select u from User u where u in (:participants))" +
                    "group by m order by m.date ";

            query = session.createQuery(command).setParameterList("participants", selectedUsers).setParameter("size", selectedUsers.size()-1)
                    .setParameter("sender", user.getLogin());
            List<Message> messages = query.list();

            transaction.commit();
            session.close();

            Conversation conversation = new Conversation();
            conversation.setMessages(messages);
            return conversation;
        }

        @Override
        public List<String> getOnlineUsers() throws RemoteException {

            List<String> list = new ArrayList<>();
            list.addAll(usersOnline.keySet());
            return list;
        }
    }

}
