package agh.core.client;

import agh.core.server.IServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientDriver {
    public static void main(String args[]) throws RemoteException, NotBoundException, MalformedURLException {
        String serverUrl = "rmi://localhost/RMIChatServer";
        IServer chatServer = (IServer) Naming.lookup(serverUrl);
        new ClientGUI(chatServer);
    }
}
