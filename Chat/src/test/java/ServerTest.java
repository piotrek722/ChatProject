import agh.client.Client;
import agh.model.User;
import agh.server.Server;
import agh.server.ServerInterface;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
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

        assertTrue(server.registerClient("User1", "password", "Name", "LastName"));


    }

    @Test
    public void unregisterUserTest() throws RemoteException {

        User user2 = new User("User2", "password", "Name", "LastName");
        User user3 = new User("User3","password","Name","LastName");
        server.registerClient("User2", "password", "Name", "LastName");

        assertFalse(server.unregisterClient(user3));
        assertTrue(server.unregisterClient(user2));



    }

    @Test
    public void loginUserTest() throws RemoteException {

        server.registerClient("User4", "password", "Name", "LastName");

        assertNotNull(server.login(client,"User4", "password"));
        assertNull(server.login(client,"User4","otherpassword"));

    }

    @Test
    public void logoutUserTest() throws RemoteException {

        User user5 = new User("User5","password", "Name", "LastName");
        User user6 = new User("User6","password", "Name", "LastName");
        server.registerClient("User5", "password", "Name", "LastName");
        server.login(client,"User5","password");

        assertTrue(server.logout(user5));
        assertFalse(server.logout(user6));
    }

    @Test
    public void addContactTest() throws RemoteException {
        User user7 = new User("User7","password", "Name", "LastName");
        User user8 = new User("User8","password", "Name", "LastName");
        server.registerClient("User7", "password", "Name", "LastName");
        server.registerClient("User8", "password", "Name", "LastName");

        assertTrue(server.addContact(user7,user7.getLogin()));
        assertTrue(server.addContact(user7,user8.getLogin()));
        assertTrue(server.addContact(user8,user7.getLogin()));
        assertTrue(server.addContact(user8,user8.getLogin()));
        assertFalse(server.addContact(user8,null));
    }

    @Test
    public void deleteContactTest() throws RemoteException {
        User user9 = new User("User9","password", "Name", "LastName");
        User user10 = new User("User10","password", "Name", "LastName");
        server.registerClient("User9", "password", "Name", "LastName");
        server.registerClient("User10", "password", "Name", "LastName");
        server.addContact(user9,user10.getLogin());

        assertTrue(server.deleteContact(user9,user10.getLogin()));
        assertTrue(server.deleteContact(user9,null));

    }

    @Test
    public void sendMessageTest() throws RemoteException {
        User sender = new User("User11","password", "Name", "LastName");
        User user12 = new User("User12", "password", "Name", "LastName");
        User user13 = new User("User13", "password", "Name", "LastName");

        server.registerClient("User11", "password", "Name", "LastName");
        server.registerClient("User12", "password", "Name", "LastName");
        server.registerClient("User13", "password", "Name", "LastName");

        server.login(client,"User11","password");
        server.login(client,"User12","password");
        server.login(client,"User13","password");

        List<String> logins = new LinkedList<>();
        logins.add("User12");
        logins.add("User13");

        Date date = new Date(151221);
        assertTrue(server.sendMessage("lalala",date,sender,logins));
    }


}
