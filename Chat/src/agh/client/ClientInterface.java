package agh.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void retreiveMessage(String message) throws RemoteException;
}
