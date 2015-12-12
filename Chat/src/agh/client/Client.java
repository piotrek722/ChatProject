package agh.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import agh.userandmessage.model.Message;
import agh.server.ServerInterface;

public class Client extends UnicastRemoteObject implements ClientInterface, Runnable {

	private static final long serialVersionUID = 1L;
	private ServerInterface server;

	protected Client(ServerInterface server) throws RemoteException {
		this.server = server;
	}

	@Override
	public void run() {
		// pass
	}

	@Override
	public void retreiveMessage(Message message) throws RemoteException {
		// TODO Auto-generated method stub
	}
}
