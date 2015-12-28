package agh.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements IClient {
    private static final long serialVersionUID = 1L;

    protected Client() throws RemoteException {
        super();
    }
}
