package agh.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import agh.userandmessage.model.Message;

public interface ClientInterface extends Remote {
    Message retreiveMessage(Message message) throws RemoteException;
}
