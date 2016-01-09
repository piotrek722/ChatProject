package agh.client.remoteobject;

import agh.model.simple.ClientMessage;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote, Serializable {
    void retrieveMessage(ClientMessage message) throws RemoteException;
}
