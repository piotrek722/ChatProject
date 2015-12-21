package agh.client;

import agh.server.ServerInterface;
import agh.userandmessage.model.Conversation;
import agh.userandmessage.model.Message;
import agh.userandmessage.model.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Client extends UnicastRemoteObject implements ClientInterface {

	private static final long serialVersionUID = 1L;
	private ServerInterface server;

	protected Client(ServerInterface server) throws RemoteException {
		this.server = server;
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
