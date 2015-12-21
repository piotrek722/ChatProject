package agh.client;

import agh.model.Message;
import agh.server.ServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Peter on 2015-12-21.
 * Project name : ChatProject
 */
public class Client extends UnicastRemoteObject implements ClientInterface {

    private static final long serialVersionUID = 1L;
    private ServerInterface server;

    public Client(ServerInterface server) throws RemoteException {
        this.server = server;
    }

    @Override
    public Message retreiveMessage(Message message) throws RemoteException {
        return message;
    }

    public ServerInterface getServer() {
        return server;
    }

    public void setServer(ServerInterface server) {
        this.server = server;
    }

}
