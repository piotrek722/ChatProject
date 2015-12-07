package agh.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
// import java.util.Scanner;

import agh.server.ServerInterface;

public class Client extends UnicastRemoteObject implements ClientInterface, Runnable {

	private static final long serialVersionUID = 1L;
	private ServerInterface server;

    protected Client(ServerInterface server) throws RemoteException {
        this.server = server;
        server.registerClient(this);
    }

    public void retreiveMessage(String message, JTextArea textArea) throws RemoteException {
        textArea.append(message + "\n");
    }

    @Override
    public void run() {
        // pass
    }
}
