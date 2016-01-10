import agh.client.remoteobject.DefaultClient;
import agh.client.remoteobject.events.*;
import agh.client.remoteobject.handlers.*;
import agh.client.gui.accountsettingsdialog.AccountSettingsDialog;
import agh.client.gui.accountsettingsdialog.events.*;
import agh.client.gui.accountsettingsdialog.handlers.*;
import agh.client.gui.conversationframe.events.*;
import agh.client.gui.conversationframe.handlers.*;
import agh.client.gui.logindialog.LoginDialog;
import agh.client.gui.logindialog.events.*;
import agh.client.gui.logindialog.handlers.*;
import agh.client.gui.mainframe.MainFrame;
import agh.client.gui.mainframe.events.*;
import agh.client.gui.mainframe.handlers.*;
import agh.client.gui.registerdialog.RegisterDialog;
import agh.client.gui.registerdialog.events.*;
import agh.client.gui.registerdialog.handlers.*;
import agh.client.gui.searchdialog.SearchDialog;
import agh.client.gui.searchdialog.events.*;
import agh.client.gui.searchdialog.handlers.*;
import agh.router.DefaultEventDispatcher;
import agh.server.Server;
import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientDriver {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, UnsupportedLookAndFeelException {
        String serverUrl = "//localhost/RMIChatServer";
        Server server = (Server) Naming.lookup(serverUrl);

        DefaultEventDispatcher eventDispatcher = new DefaultEventDispatcher();

        MainFrame mainFrame = new MainFrame(eventDispatcher);
        LoginDialog login = new LoginDialog(eventDispatcher);
        RegisterDialog register = new RegisterDialog(eventDispatcher);
        SearchDialog searchDialog = new SearchDialog(eventDispatcher);
        AccountSettingsDialog accountSettings = new AccountSettingsDialog(eventDispatcher);
        DefaultClient client = new DefaultClient(eventDispatcher);

        //MainFrame events
        eventDispatcher.registerChannel(CloseEvent.class, new CloseHandler(server));
        eventDispatcher.registerChannel(DeleteContactsEvent.class, new DeleteContactsHandler(server, mainFrame));
        eventDispatcher.registerChannel(LogoutEvent.class, new LogoutHandler(server, mainFrame, login));
        eventDispatcher.registerChannel(ShowAccountSettingsEvent.class, new ShowAccountSettingsHandler(accountSettings));
        eventDispatcher.registerChannel(ShowSearchEvent.class, new ShowSearchHandler(searchDialog));

        //ConversationFrame events
        eventDispatcher.registerChannel(SendMessageEvent.class, new SendMessageHandler(server));
        eventDispatcher.registerChannel(GetConversationEvent.class, new GetConversationHandler(server));

        //LoginDialog events
        eventDispatcher.registerChannel(LoginEvent.class, new LoginHandler(server, client, login, mainFrame));
        eventDispatcher.registerChannel(SwitchLoginToRegisterEvent.class, new SwitchLoginToRegisterHandler(login, register));
        eventDispatcher.registerChannel(GetContactsEvent.class, new GetContactsHandler(server, mainFrame));

        //RegisterDialog events
        eventDispatcher.registerChannel(RegisterEvent.class, new RegisterHandler(server, register, login));
        eventDispatcher.registerChannel(SwitchRegisterToLoginEvent.class, new SwitchRegisterToLoginHandler(register, login));

        //SearchDialog events
        eventDispatcher.registerChannel(SearchEvent.class, new SearchHandler(server, searchDialog));
        eventDispatcher.registerChannel(AddContactEvent.class, new AddContactHandler(server, mainFrame));

        //AccountSettingsDialog events
        eventDispatcher.registerChannel(SaveAccountChangesEvent.class, new SaveAccountChangesHandler(server, mainFrame, accountSettings));
        eventDispatcher.registerChannel(ChangePasswordEvent.class, new ChangePasswordHandler(server, accountSettings));

        //Client events
        eventDispatcher.registerChannel(RetrieveMessageEvent.class, new RetrieveMessageHandler(mainFrame));

        login.setVisible(true);
    }
}
