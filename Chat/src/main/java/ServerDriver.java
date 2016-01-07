import agh.server.DefaultServer;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ServerDriver {
    private static final int PORT = 1099;
    public static void main(String[] args) throws RemoteException, MalformedURLException, UnsupportedLookAndFeelException {
        LocateRegistry.createRegistry(PORT);
        Naming.rebind("RMIChatServer", new DefaultServer());
    }
}
