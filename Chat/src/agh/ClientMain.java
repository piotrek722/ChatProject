package agh;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import agh.ServerInterface;

/**
 * Created by Peter on 2015-11-20.
 * Project name : ChatProject
 */
public class ClientMain {

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        String serverURL = "rmi://localhost/RMIServer";
	ServerInterface server = (ServerInterface) Naming.lookup(serverURL);
	new Thread(new Client(args[0], server)).start();
    }
}
