package agh.eventshandlers;

import agh.router.Channel;
import agh.server.IServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public abstract class GUIHandler implements Channel {
    private static final String serverURL = "//localhost/RMIChatServer";
    private IServer server;

    public GUIHandler() {
        try {
            this.server = (IServer) Naming.lookup(serverURL);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
