package agh.client.remoteobject;

import agh.model.Message;
import agh.client.remoteobject.events.ReceiveMessageEvent;
import agh.router.DefaultEventDispatcher;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DefaultClient extends UnicastRemoteObject implements Client {
    private static final long serialVersionUID = 1L;
    private DefaultEventDispatcher dispatcher;

    public DefaultClient(DefaultEventDispatcher dispatcher) throws RemoteException {
        super();
        this.dispatcher = dispatcher;
    }

    @Override
    public Message retreiveMessage(Message message) throws RemoteException {
        //dispatcher.dispatch(new ReceiveMessageEvent());
        return null;
    }
}
