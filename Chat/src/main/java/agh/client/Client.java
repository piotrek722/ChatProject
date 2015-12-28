package agh.client;

import agh.router.EventDispatcher;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements IClient {
    private static final long serialVersionUID = 1L;
    private EventDispatcher dispatcher;

    public Client(EventDispatcher dispatcher) throws RemoteException {
        super();
        this.dispatcher = dispatcher;
    }
}
