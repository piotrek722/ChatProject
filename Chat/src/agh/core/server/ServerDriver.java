package agh.core.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ServerDriver {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        ServerGUI serverGUI = new ServerGUI();
        Naming.rebind("RMIChatServer", serverGUI.getServer());
    }
}
