package agh.core.client;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote, Serializable {
    //Temporary for CORE tests
    void retrieveMessage(String message) throws RemoteException;
    String getLogin() throws RemoteException;
}
