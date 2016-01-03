package agh.server;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by Peter on 2015-11-21.
 * Project name : ChatProject
 */
public class ServerMain {
    private static final int PORT = 1099;
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        LocateRegistry.createRegistry(PORT);
        Naming.rebind("RMIServer", new Server());
        System.out.println("Server started...");
    }
}
