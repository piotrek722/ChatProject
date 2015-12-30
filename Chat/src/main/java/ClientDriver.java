import agh.client.Client;
import agh.clientgui.ClientGUI;
import agh.clientgui.LoginDialog;
import agh.clientgui.RegisterDialog;
import agh.clientgui.SearchDialog;
import agh.events.*;
import agh.handlers.*;
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

        EventDispatcher eventDispatcher = new EventDispatcher();

        ClientGUI clientGUI = new ClientGUI(eventDispatcher);
        LoginDialog login = new LoginDialog(eventDispatcher);
        RegisterDialog register = new RegisterDialog(eventDispatcher);
        SearchDialog searchDialog = new SearchDialog(eventDispatcher);
        Client client = new Client(eventDispatcher);

        eventDispatcher.registerChannel(DeleteContactEvent.class, new DeleteContactHandler(server));
        eventDispatcher.registerChannel(LoginEvent.class, new LoginHandler(server, client, login, clientGUI));
        eventDispatcher.registerChannel(ReceiveMessageEvent.class, new ReceiveMessageHandler(clientGUI));
        eventDispatcher.registerChannel(RegisterEvent.class, new RegisterHandler(server, register, login));
        eventDispatcher.registerChannel(SearchEvent.class, new SearchHandler(server, searchDialog));
        eventDispatcher.registerChannel(SendMessageEvent.class, new SendMessageHandler(server));
        eventDispatcher.registerChannel(ShowSearchEvent.class, new ShowSearchHandler(searchDialog));
        eventDispatcher.registerChannel(SwitchLoginRegisterEvent.class, new SwitchLoginRegisterHandler(login, register));

        login.setVisible(true);
    }
}
