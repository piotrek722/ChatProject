package agh.client;

import agh.model.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    Message retreiveMessage(Message message) throws RemoteException;
}
