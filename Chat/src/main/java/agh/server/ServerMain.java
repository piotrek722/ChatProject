package agh.server;

import agh.persistance.HibernateUtils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by Peter on 2015-11-21.
 * Project name : ChatProject
 */
public class ServerMain {

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        Naming.rebind("RMIServer", new Server());
        System.out.println("Server started...");
    }
}
