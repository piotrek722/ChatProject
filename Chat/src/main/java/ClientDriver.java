import agh.clientgui.ClientGUI;
import agh.clientgui.Login;
import agh.clientgui.Register;
import agh.guievents.*;
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

        ClientGUI clientGUI = new ClientGUI(guiEventDispatcher);
        Login login = new Login(guiEventDispatcher);
        Register register = new Register(guiEventDispatcher);

        guiEventDispatcher.registerChannel(LoginEvent.class, new LoginHandler(server));
        guiEventDispatcher.registerChannel(RegisterEvent.class, new RegisterHandler(server));
        guiEventDispatcher.registerChannel(SearchEvent.class, new SearchHandler(server));
        guiEventDispatcher.registerChannel(SendMessageEvent.class, new SendMessageHandler(server));

        //new ClientGUI(guiEventDispatcher);
        new Login(guiEventDispatcher).setVisible(true);
    }
}
