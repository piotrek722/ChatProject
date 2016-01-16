import agh.client.remoteobject.DefaultClient;
import agh.model.db.ContactList;
import agh.model.db.Message;
import agh.model.db.User;
import agh.model.simple.SimplifiedUser;
import agh.persistance.HibernateUtils;
import agh.router.DefaultEventDispatcher;
import agh.server.DefaultServer;
import agh.server.Server;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
 * Created by Asia on 2016-01-09.
 */
public class ServerTest {

    private static Server server;
    private static DefaultClient client;

    @BeforeClass
    public static void beforeClass()  throws MalformedURLException, RemoteException, NotBoundException {

        LocateRegistry.createRegistry(1099);
        Naming.rebind("RMIServer", new DefaultServer("src/test/resources/hibernate.cfg.xml"));
        String serverURL = "rmi://localhost/RMIServer";
        server = (Server) Naming.lookup(serverURL);
        DefaultEventDispatcher eventDispatcher = new DefaultEventDispatcher();
        client = new DefaultClient(eventDispatcher);

    }

    @Test
    public void registerUserTest() throws RemoteException {

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

        assertFalse(server.unregisterClient(user3.getLogin()));
        assertTrue(server.unregisterClient(user2.getLogin()));

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
    public void addContactTest() throws RemoteException {

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        server.registerClient("User7", "password", "Name", "LastName");
        server.registerClient("User8", "password", "Name", "LastName");
        server.registerClient("User9", "password", "Name", "LastName");

        server.login(client,"User7","password");

        String commandu = "select u from User u where u.login like :login";
        Query queryu = session.createQuery(commandu).setParameter("login","User7");
        List<User> users = queryu.list();

        transaction.commit();
        session.close();

        server.addContact(users.get(0).getLogin(), "User8");
        server.addContact(users.get(0).getLogin(), "User9");

        session = HibernateUtils.getSession();
        transaction = session.beginTransaction();

        String command = "select u from User u where u.login like :login";
        Query query = session.createQuery(command).setParameter("login","User7");
        User us = (User) query.list().get(0);

        transaction.commit();
        session.close();

        assertEquals(2,us.getContactList().getUserList().size());

    }

    @Test
    public void deleteContactTest() throws RemoteException {

        server.registerClient("User990", "password", "Name", "LastName");
        server.registerClient("User100", "password", "Name", "LastName");
        server.registerClient("User110", "password", "Name", "LastName");

        server.login(client,"User990","password");

        Session session1 = HibernateUtils.getSession();
        Transaction transaction1 = session1.beginTransaction();

        String command1 = "select u from User u where u.login like :login";
        Query query1 = session1.createQuery(command1).setParameter("login","User990");
        User user1 = (User) query1.list().get(0);

        transaction1.commit();
        session1.close();

        server.addContact(user1.getLogin(),"User100");
        server.addContact(user1.getLogin(),"User110");

        session1 = HibernateUtils.getSession();
        transaction1 = session1.beginTransaction();

        String command2 = "select u from User u where u.login like :login";
        Query query2 = session1.createQuery(command2).setParameter("login","User990");
        User user2 = (User) query2.list().get(0);

        transaction1.commit();
        session1.close();

        assertEquals(2,user2.getContactList().getUserList().size());

        session1 = HibernateUtils.getSession();
        transaction1 = session1.beginTransaction();

        String command4 = "select u from User u where u.login like :login";
        Query query4 = session1.createQuery(command4).setParameter("login","User990");
        User user4 = (User) query4.list().get(0);

        transaction1.commit();
        session1.close();

        assertTrue(server.deleteContact(user4.getLogin(),"User110"));

        session1 = HibernateUtils.getSession();
        transaction1 = session1.beginTransaction();

        String command3 = "select cl from User u inner join u.contactList cl where u.login like :login";
        Query query3 = session1.createQuery(command3).setParameter("login","User990");
        ContactList contactListNew = (ContactList) query3.list().get(0);

        transaction1.commit();
        session1.close();

        assertEquals(1,contactListNew.getUserList().size());


    }



    @Test
    public void getContactsTest() throws RemoteException {

        server.registerClient("User14", "password", "Name", "LastName");
        server.registerClient("User15", "password", "Name", "LastName");
        server.registerClient("User16", "password", "Name", "LastName");

        server.login(client,"User14","password");
        server.addContact("User14", "User15");
        server.addContact("User14", "User16");

        assertEquals(2,server.getContacts("User14").getUserList().size());

    }

    @Test
    public void findUserTest() throws RemoteException {
        server.registerClient("User20", "password", "Name0", "LastName0");
        server.registerClient("User21", "password", "Name1", "LastName1");
        server.registerClient("User22", "password", "Name2", "LastName2");

        server.login(client,"User20","password");
        server.login(client,"User21","password");
        server.login(client,"User22","password");

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        String command = "select u from User u where u.name like :name";
        Query query = session.createQuery(command).setParameter("name","Name0");
        List<User> found = query.list();

        assertEquals(found.get(0).getLogin(),server.findUser("","Name0","").get(0).getLogin());

        String command2 = "select u from User u where u.lastName like :lastname";
        Query query2 = session.createQuery(command2).setParameter("lastname","LastName1");
        List<User> found2 = query2.list();

        assertEquals(found2.get(0).getLogin(),server.findUser("","","LastName1").get(0).getLogin());

        String command3 = "select u from User u where u.login like :login";
        Query query3 = session.createQuery(command3).setParameter("login","User20");
        List<User> found3 = query3.list();

        assertEquals(found3.get(0).getLogin(),server.findUser("User20","","").get(0).getLogin());

        String command4 = "select u from User u where u.lastName like :lastname and u.name like :name";
        Query query4 = session.createQuery(command4).setParameter("lastname","LastName1").setParameter("name","Name1");
        List<User> found4 = query4.list();

        assertEquals(found4.get(0).getLogin(),server.findUser("","Name1","LastName1").get(0).getLogin());

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

        List<SimplifiedUser> users = new ArrayList<>();
        for (User u : found) {
            users.add(new SimplifiedUser(u.getLogin(),u.getName(),u.getLastName()));
        }

        assertTrue(users.containsAll(server.getAllUsers()));

        transaction.commit();
        session.close();
    }


}
