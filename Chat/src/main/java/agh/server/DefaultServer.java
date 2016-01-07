package agh.server;

import agh.client.remoteobject.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DefaultServer extends UnicastRemoteObject implements Server {
    private static final long serialVersionUID = 1L;
    List<Client> clients;

    public DefaultServer() throws RemoteException {
        super();
        clients = new ArrayList<>();
    }

    //User&Messages
}
