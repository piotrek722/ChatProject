package agh.core.client;

import agh.core.server.IServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientDriver {
    public static void main(String args[]) throws RemoteException, NotBoundException, MalformedURLException {
        String serverUrl = "//localhost/RMIChatServer";
        IServer server = (IServer) Naming.lookup(serverUrl);
        new ClientGUI(server);
    }
}
