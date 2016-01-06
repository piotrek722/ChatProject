import agh.client.Client;
import agh.model.ContactList;
import agh.model.Conversation;
import agh.model.Message;
import agh.model.User;
import agh.persistance.HibernateUtils;
import agh.server.Server;
import agh.server.ServerInterface;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Asia on 2015-12-19.
 */

public class ServerTest {

    private static ServerInterface server;
    private static Client client;

    @BeforeClass
    public static void beforeClass()  throws MalformedURLException, RemoteException, NotBoundException {

        LocateRegistry.createRegistry(1099);
        Naming.rebind("RMIServer", new Server("src/test/resources/hibernate.cfg.xml"));
        String serverURL = "rmi://localhost/RMIServer";
        server = (ServerInterface) Naming.lookup(serverURL);
        client = new Client(server);

    }

    @Test
    public void registerUserTest() throws RemoteException {

        //User user1 = new User("User1", "password", "Name", "LastName");
        assertTrue(server.registerClient("User1", "password", "Name", "LastName"));

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login like :login";
        Query query = session.createQuery(command).setParameter("login", "User1");
        List<User> found = query.list();

        if(found.isEmpty()) {
            fail();
        }

        assertEquals("User1",found.get(0).getLogin());
        assertEquals("password",found.get(0).getPassword());
        assertEquals("Name",found.get(0).getName());
        assertEquals("LastName",found.get(0).getLastName());

        transaction.commit();
        session.close();


    }

    @Test
    public void unregisterUserTest() throws RemoteException {

        User user2 = new User("User2", "password", "Name", "LastName");
        User user3 = new User("User3","password","Name","LastName");

        assertTrue(server.registerClient("User2", "password", "Name", "LastName"));

        assertFalse(server.unregisterClient(user3));
        assertTrue(server.unregisterClient(user2));

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login like :login";
        Query query = session.createQuery(command).setParameter("login", "User2");
        List<User> found = query.list();

        if(!found.isEmpty()) {
            fail();
        }

        transaction.commit();
        session.close();


    }

    @Test
    public void loginUserTest() throws RemoteException {

        assertTrue(server.registerClient("User4", "password", "Name", "LastName"));

        assertNotNull(server.login(client,"User4", "password"));
        assertNull(server.login(client,"User4","otherpassword"));

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login like :login and u.password like :password";
        Query query = session.createQuery(command).setParameter("login", "User4").setParameter("password", "password");
        List<User> found = query.list();

        assertEquals("User4",found.get(0).getLogin());
        assertEquals("password",found.get(0).getPassword());
        assertEquals("Name",found.get(0).getName());
        assertEquals("LastName",found.get(0).getLastName());

        assertNotNull(server.getUsersOnline());

        transaction.commit();
        session.close();

    }

    @Test
    public void logoutUserTest() throws RemoteException {

        server.registerClient("User5", "password", "Name", "LastName");
        server.login(client,"User5","password");

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.login like :login";
        Query query = session.createQuery(command).setParameter("login", "User5");
        List<User> found = query.list();


        assertTrue(server.logout(found.get(0)));
        for (User user : server.getUsersOnline()) {
            assertNotEquals(found.get(0),user);
        }

        transaction.commit();
        session.close();
    }

    @Test
    public void addContactTest() throws RemoteException {

        server.registerClient("User7", "password", "Name", "LastName");
        server.registerClient("User8", "password", "Name", "LastName");
        server.registerClient("User9", "password", "Name", "LastName");

        User user7 = server.login(client,"User7","password");
        server.addContact(user7, "User8");
        server.addContact(user7, "User9");

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select cl from User u inner join u.contactList cl where u.login like :login";
        Query query = session.createQuery(command).setParameter("login","User7");
        ContactList contactList = (ContactList) query.list().get(0);

        assertEquals(2,contactList.getUserList().size());

        transaction.commit();
        session.close();
    }

    @Test
    public void deleteContactTest() throws RemoteException {

        server.registerClient("User9", "password", "Name", "LastName");
        server.registerClient("User10", "password", "Name", "LastName");
        server.registerClient("User11", "password", "Name", "LastName");

        User user9 = server.login(client,"User9","password");

        server.addContact(user9,"User10");
        server.addContact(user9,"User11");

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select cl from User u inner join u.contactList cl where u.login like :login";
        Query query = session.createQuery(command).setParameter("login","User9");
        ContactList contactList = (ContactList) query.list().get(0);

        transaction.commit();
        session.close();

        assertEquals(2,contactList.getUserList().size());

        assertTrue(server.deleteContact(user9,"User11"));

        Session session2 = HibernateUtils.getSession();
        Transaction transaction2 = session2.beginTransaction();

        String command2 = "select cl from User u inner join u.contactList cl where u.login like :login";
        Query query2 = session2.createQuery(command2).setParameter("login","User9");
        ContactList contactListNew = (ContactList) query2.list().get(0);


        assertEquals(1,contactListNew.getUserList().size());

        transaction2.commit();
        session2.close();
    }

    @Test
    public void sendMessageTest() throws RemoteException {

        server.registerClient("User11", "password", "Name", "LastName");
        server.registerClient("User12", "password", "Name", "LastName");
        server.registerClient("User13", "password", "Name", "LastName");

        User user11 = server.login(client,"User11","password");
        User user12 = server.login(client,"User12","password");
        User user13 = server.login(client,"User13","password");

        List<String> logins = new LinkedList<>();
        logins.add("User12");
        logins.add("User13");

        Date date = new Date(151221);
        assertTrue(server.sendMessage("lalala",date,user11,logins));

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select m from Message m inner join m.sender s where s.login like :login";
        Query query = session.createQuery(command).setParameter("login","User11");
        List<Message> found = query.list();

        List<User> receivers = new ArrayList<>();
        receivers.add(user12);
        receivers.add(user13);

        assertEquals(user11,found.get(0).getSender());
        assertEquals("lalala",found.get(0).getContent());
        assertEquals(receivers,found.get(0).getReceivers());

        transaction.commit();
        session.close();

    }

    @Test
    public void getContactsTest() throws RemoteException {

        server.registerClient("User14", "password", "Name", "LastName");
        server.registerClient("User15", "password", "Name", "LastName");
        server.registerClient("User16", "password", "Name", "LastName");

        User user14 = server.login(client,"User14","password");
        server.addContact(user14, "User15");
        server.addContact(user14, "User16");

        assertEquals(2,server.getContacts(user14).getUserList().size());

    }

    @Test
    public void getMessagesTest() throws RemoteException {
        server.registerClient("User17", "password", "Name", "LastName");
        server.registerClient("User18", "password", "Name", "LastName");
        server.registerClient("User19", "password", "Name", "LastName");

        User user17 = server.login(client,"User17","password");
        User user18 = server.login(client,"User18","password");
        User user19 = server.login(client,"User19","password");

        List<String> logins = new ArrayList<>();
        logins.add("User18");

        Date date = new Date(151221);

        assertTrue(server.sendMessage("message1",date,user17,logins));      //17 do 18

        logins.add("User19");

        assertTrue(server.sendMessage("message2",date,user17,logins));      //17 do 18 i 19

        logins.remove("User18");

        assertTrue(server.sendMessage("message3",date,user17,logins));      //17 do 19

        logins.remove("User19");
        logins.add("User17");

        assertTrue(server.sendMessage("message4",date,user18,logins));      //18 do 17

        List<String> selectedUsers = new ArrayList<>();
        selectedUsers.add("User18");

        Conversation c = server.getMessages(user17,selectedUsers);      //miedzy 17 a 18 => 1,4

        for (Message m : c.getMessages()) {
            if (!(m.getSender().getLogin().equals(user17.getLogin())  || m.getSender().getLogin().equals(user18.getLogin()) )) {
                fail("sender");
            }
            if (!(m.getReceivers().contains(user17) || m.getReceivers().contains(user18))) {
                fail("receivers");
            }
            if (m.getReceivers().size() != 1) {
                fail("size");
            }
        }

        assertEquals(2,c.getMessages().size());

    }

    @Test
    public void findUserTest() throws RemoteException {
        server.registerClient("User20", "password", "Name0", "LastName0");
        server.registerClient("User21", "password", "Name1", "LastName1");
        server.registerClient("User22", "password", "Name2", "LastName2");

        User user20 = server.login(client,"User20","password");
        User user21 = server.login(client,"User21","password");
        User user22 = server.login(client,"User22","password");

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.name like :name";
        Query query = session.createQuery(command).setParameter("name","Name0");
        List<User> found = query.list();

        assertEquals(found,server.findUser("","Name0",""));

        String command2 = "select u from User u where u.lastName like :lastname";
        Query query2 = session.createQuery(command2).setParameter("lastname","LastName1");
        List<User> found2 = query2.list();

        assertEquals(found2,server.findUser("","","LastName1"));

        String command3 = "select u from User u where u.login like :login";
        Query query3 = session.createQuery(command3).setParameter("login","User20");
        List<User> found3 = query3.list();

        assertEquals(found3,server.findUser("User20","",""));

        String command4 = "select u from User u where u.lastName like :lastname and u.name like :name";
        Query query4 = session.createQuery(command4).setParameter("lastname","LastName1").setParameter("name","Name1");
        List<User> found4 = query4.list();

        assertEquals(found4,server.findUser("","Name1","LastName1"));

        transaction.commit();
        session.close();
    }

    @Test
    public void getAllUsersTest() throws RemoteException {

        server.registerClient("User23", "password", "Name", "LastName");
        server.registerClient("User24", "password", "Name", "LastName");

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("select u from User u");
        List<User> found = query.list();

        assertEquals(found,server.getAllUsers());

        transaction.commit();
        session.close();
    }


}
