package agh.server;

import agh.client.IClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Server extends UnicastRemoteObject implements IServer {
    private static final long serialVersionUID = 1L;
    List<IClient> clients;

    public Server() throws RemoteException {
        super();
        clients = new ArrayList<>();
    }

    //User&Messages
}
