package agh.core.server;

import agh.core.client.IClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
    void registerClient(IClient client) throws RemoteException;
    void broadcastMessage(String message) throws RemoteException;
    void retrieveMessage(String message) throws RemoteException;
}
