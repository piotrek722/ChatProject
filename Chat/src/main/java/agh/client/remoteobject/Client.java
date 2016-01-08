package agh.client.remoteobject;

import agh.model.Message;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote, Serializable {
    void retreiveMessage(Message message) throws RemoteException;
}
