import agh.core.server.ServerGUI;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ServerDriver {
    public static void main(String[] args) throws RemoteException, MalformedURLException, UnsupportedLookAndFeelException {
        ServerGUI serverGUI = new ServerGUI();
        Naming.rebind("RMIChatServer", serverGUI.getServer());
    }
}
