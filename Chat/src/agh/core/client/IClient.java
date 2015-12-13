package agh.core.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    //Temporary for CORE tests
    void retrieveMessage(String message) throws RemoteException;
    String getLogin() throws RemoteException;
}
