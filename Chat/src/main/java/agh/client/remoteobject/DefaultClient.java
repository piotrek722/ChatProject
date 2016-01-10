package agh.client.remoteobject;

import agh.client.remoteobject.events.RetrieveMessageEvent;
import agh.model.simple.ClientMessage;
import agh.router.DefaultEventDispatcher;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DefaultClient extends UnicastRemoteObject implements Client, Serializable {
    private static final long serialVersionUID = 1L;
    private DefaultEventDispatcher dispatcher;

    public DefaultClient(DefaultEventDispatcher dispatcher) throws RemoteException {
        super();
        this.dispatcher = dispatcher;
    }

    @Override
    public void retrieveMessage(ClientMessage message) throws RemoteException {
        dispatcher.dispatch(new RetrieveMessageEvent(message));
    }
}
