import agh.client.Client;
import agh.client.gui.*;
import agh.client.events.*;
import agh.client.handlers.*;
import agh.router.DefaultEventDispatcher;
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

        DefaultEventDispatcher eventDispatcher = new DefaultEventDispatcher();

        ClientGUI clientGUI = new ClientGUI(eventDispatcher);
        LoginDialog login = new LoginDialog(eventDispatcher);
        RegisterDialog register = new RegisterDialog(eventDispatcher);
        SearchDialog searchDialog = new SearchDialog(eventDispatcher);
        AccountSettingsDialog accountSettings = new AccountSettingsDialog(eventDispatcher);
        Client client = new Client(eventDispatcher);

        //ClientGUI events
        eventDispatcher.registerChannel(DeleteContactEvent.class, new DeleteContactHandler(server));
        eventDispatcher.registerChannel(LogoutEvent.class, new LogoutHandler(clientGUI, login));
        eventDispatcher.registerChannel(ShowAccountSettingsEvent.class, new ShowAccountSettingsHandler(accountSettings));
        eventDispatcher.registerChannel(ShowSearchEvent.class, new ShowSearchHandler(searchDialog));
        //ConversationWindow events
        eventDispatcher.registerChannel(SendMessageEvent.class, new SendMessageHandler(server));
        eventDispatcher.registerChannel(GetConversationEvent.class, new GetConversationHandler(server));
        //LoginDialog events
        eventDispatcher.registerChannel(LoginEvent.class, new LoginHandler(server, client, login, clientGUI));
        eventDispatcher.registerChannel(SwitchLoginToRegisterEvent.class, new SwitchLoginToRegisterHandler(login, register));
        eventDispatcher.registerChannel(GetContactsEvent.class, new GetContactsHandler(server, clientGUI));
        //RegisterDialog events
        eventDispatcher.registerChannel(RegisterEvent.class, new RegisterHandler(server, register, login));
        eventDispatcher.registerChannel(SwitchRegisterToLoginEvent.class, new SwitchRegisterToLoginHandler(register, login));
        //SearchDialog events
        eventDispatcher.registerChannel(SearchEvent.class, new SearchHandler(server, searchDialog));
        eventDispatcher.registerChannel(AddContactEvent.class, new AddContactHandler(server, clientGUI));
        //AccountSettingsDialog events
        eventDispatcher.registerChannel(SaveAccountChangesEvent.class, new SaveAccountChangesHandler(server, accountSettings));
        eventDispatcher.registerChannel(ChangePasswordEvent.class, new ChangePasswordHandler(server, accountSettings));
        //Client events
        eventDispatcher.registerChannel(ReceiveMessageEvent.class, new ReceiveMessageHandler(clientGUI));

        login.setVisible(true);
    }
}
