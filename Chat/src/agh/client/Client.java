package agh.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import agh.userandmessage.model.Message;
import agh.server.ServerInterface;

public class Client extends UnicastRemoteObject implements ClientInterface {

	private static final long serialVersionUID = 1L;
	private ServerInterface server;

	protected Client(ServerInterface server) throws RemoteException {
		this.setServer(server);
	}

	@Override
	public Message retreiveMessage(Message message) throws RemoteException {
		return message;
	}

	public ServerInterface getServer() {
		return server;
	}

	public void setServer(ServerInterface server) {
		this.server = server;
	}
}
