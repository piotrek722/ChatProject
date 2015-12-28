import agh.clientgui.ClientGUI;
import agh.eventshandlers.*;
import agh.router.EventDispatcher;
import agh.server.IServer;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientDriver {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, UnsupportedLookAndFeelException {
        String serverUrl = "//localhost/RMIChatServer";
        IServer server = (IServer) Naming.lookup(serverUrl);

        EventDispatcher guiEventDispatcher = new EventDispatcher();
        guiEventDispatcher.registerChannel(LoginEvent.class, new LoginHandler(server));
        guiEventDispatcher.registerChannel(RegisterEvent.class, new RegisterHandler(server));
        guiEventDispatcher.registerChannel(SearchEvent.class, new SearchHandler(server));
        guiEventDispatcher.registerChannel(SendMessageEvent.class, new SendMessageHandler(server));

        new ClientGUI();
    }
}
