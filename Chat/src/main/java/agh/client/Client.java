package agh.client;

import agh.router.DefaultEventDispatcher;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements IClient {
    private static final long serialVersionUID = 1L;
    private DefaultEventDispatcher dispatcher;

    public Client(DefaultEventDispatcher dispatcher) throws RemoteException {
        super();
        this.dispatcher = dispatcher;
    }
}
