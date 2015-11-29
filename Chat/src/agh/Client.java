package agh;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import agh.ServerInterface;

public class Client extends UnicastRemoteObject implements ClientInterface, Runnable {

	private static final long serialVersionUID = 1L;
	private ServerInterface server;

	protected Client(ServerInterface server) throws RemoteException {
		this.server = server;
	}

	@Override
	public void run() {
		//pass
	}
}
