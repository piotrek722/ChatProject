package agh.client;

import agh.server.ServerInterface;
import agh.model.Message;
import agh.model.User;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class ClientGUI extends JFrame {
    private JPanel mainPanel;
    private JList contactJList;
    private DefaultListModel<String> contacts;
    private JTabbedPane tabbedPane;
    private JPopupMenu popupMenu;

    private ServerInterface server;
    private Client client;
    private User user;

    private static final Logger LOGGER = Logger.getLogger("Logger");
    private static final int CLIENT_WIDTH = 600;
    private static final int CLIENT_HEIGHT = 400;

    public ClientGUI(final ServerInterface server) throws RemoteException, UnsupportedLookAndFeelException {
        super("Chat");
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        setContentPane(mainPanel);
        setSize(CLIENT_WIDTH, CLIENT_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.server = server;

        createMenuBar();
        createPopupMenu();

        contactJList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    startConversationWithChosenContacts();
                }
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //User&Messages
            //Unregister Client
            //server.unregisterClient(user);
        }));

        new Login().setVisible(true);
        setVisible(true);
    }

    private void createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Menu");

        JMenuItem searchContact = new JMenuItem("Search contact");
        searchContact.addActionListener(e ->  new Search().setVisible(true));
        file.add(searchContact);

        JMenuItem accountSettings = new JMenuItem("Account Settings");
        //ACTIONLISTENER
        file.add(accountSettings);

        file.addSeparator();

        JMenuItem logout = new JMenuItem("Log out");
        logout.addActionListener(e -> onLogOut());
        file.add(logout);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> onExit());
        file.add(exit);

        menuBar.add(file);
        this.setJMenuBar(menuBar);
    }

    private void createPopupMenu(){
        this.popupMenu = new JPopupMenu();

        JMenuItem startConversation = new JMenuItem("Start conversation");
        startConversation.addActionListener(e -> startConversationWithChosenContacts());
        popupMenu.add(startConversation);

        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(e -> onDeleteContact());
        popupMenu.add(delete);

        contactJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = contactJList.locationToIndex(new Point(e.getX(), e.getY()));
                    if (!contactJList.isSelectedIndex(index)) {
                        contactJList.setSelectedIndex(index);
                    }
                    popupMenu.show(contactJList, e.getX(), e.getY());
                }
            }
        });
    }

    private void createUIComponents() {
        contacts = new DefaultListModel<>();
        contactJList = new JList(contacts);
    }

    private void onExit() {
        onLogOut();
        //Exit
    }

    private void onLogOut() {
        //LogOut
        try {
            server.logout(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void onDeleteContact() {
        List<Object> values = contactJList.getSelectedValuesList();
        for (Object elem : values) {
            contacts.removeElement(elem);
        }
    }

    private void startConversationWithChosenContacts() {
        List<String> chosenContacts = new ArrayList<>();
        String text = "";

        for (int index : contactJList.getSelectedIndices()) {
            chosenContacts.add(contacts.elementAt(index));
            text += contacts.elementAt(index) + "; ";
        }

        ConversationTab conversationTab = new ConversationTab(); //Component for Tab
        conversationTab.setContacts(chosenContacts); //Contacts for conversation tab
        conversationTab.setLabelText(text); //List of contacts for conversation tab

        //Check if the conversationTab is already opened
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            List<String> conversationContactsList;
            conversationContactsList = ((ConversationTab)tabbedPane.getComponentAt(i)).getContacts();
            if (Arrays.equals(chosenContacts.toArray(), conversationContactsList.toArray())) {
                tabbedPane.setSelectedIndex(i);
                return;
            }
        }

        //It is not opened
        if (chosenContacts.size() == 1) {
            tabbedPane.addTab(chosenContacts.get(0), conversationTab);
            tabbedPane.setSelectedComponent(conversationTab);
        } else if (chosenContacts.size() > 1){
            tabbedPane.addTab("Conference", conversationTab);
            tabbedPane.setSelectedComponent(conversationTab);
        }
    }

    //////////////////////

    public class Login extends JDialog {
        private JPanel contentPane;
        private JButton signInButton;
        private JTextField loginTextField;
        private JPasswordField passwordTextField;
        private JButton signUpButton;

        private static final int LOGIN_WIDTH = 300;
        private static final int LOGIN_HEIGHT = 300;

        public Login() {
            setContentPane(contentPane);
            setModal(true);
            setSize(LOGIN_WIDTH, LOGIN_HEIGHT);
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            signInButton.addActionListener(e -> onSignIn());
            signUpButton.addActionListener(e -> onSignUp());

            KeyAdapter keyadapter = new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == '\n') {
                        onSignIn();
                    }
                }
            };

            loginTextField.addKeyListener(keyadapter);
            passwordTextField.addKeyListener(keyadapter);
        }

        private void onSignIn() {
            String login = loginTextField.getText();
            char[] password = passwordTextField.getPassword();
            String pass = new String(password);

            //User&Messages
            try {
                user = server.login(client,login,pass);
            } catch (RemoteException e) {
                e.printStackTrace();
            }


            if (user == null) {
                //wait till password is correct
            }

            dispose();

        }

        private void onSignUp() {
            dispose();
            new Register().setVisible(true);
        }
    }

    public class Register extends JDialog {
        private JPanel contentPane;
        private JButton signUpButton;
        private JButton signInButton;
        private JTextField loginTextField;
        private JPasswordField passwordTextField;
        private JTextField firstNameTextField;
        private JTextField lastNameTextField;

        private static final int REGISTER_WIDTH = 400;
        private static final int REGISTER_HEIGHT = 400;

        public Register() {
            setContentPane(contentPane);
            setModal(true);
            setSize(REGISTER_WIDTH, REGISTER_HEIGHT);
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            KeyAdapter keyadapter = new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == '\n') {
                        onSignIn();
                    }
                }
            };

            signUpButton.addActionListener(e -> onSignUp());
            signInButton.addActionListener(e -> onSignIn());

            loginTextField.addKeyListener(keyadapter);
            passwordTextField.addKeyListener(keyadapter);
            firstNameTextField.addKeyListener(keyadapter);
            lastNameTextField.addKeyListener(keyadapter);
        }

        private void onSignUp() {
            String login = loginTextField.getText();
            char[] password = passwordTextField.getPassword();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();

            //User&Messages
            String pass = new String(password);
            try {
                server.registerClient(login,pass,firstName,lastName);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            dispose();
        }

        private void onSignIn() {
            dispose();
            new Login().setVisible(true);
        }
    }

    public class Search extends JDialog {
        private JPanel contentPane;
        private JTextField nickTextField;
        private JTextField lastNameTextField;
        private JTextField firstNameTextField;
        private JButton searchButton;
        private JButton addButton;
        private JTable resultsTable;

        private static final int SEARCH_WIDTH = 500;
        private static final int SEARCH_HEIGHT = 200;

        public Search() {
            setContentPane(contentPane);
            setModal(true);
            setSize(SEARCH_WIDTH, SEARCH_HEIGHT);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            KeyAdapter keyadapter = new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == '\n') {
                        Object source = e.getSource();
                        if (source == nickTextField || source == firstNameTextField || source == lastNameTextField) {
                            onSearch();
                        } else if(source == resultsTable) {
                            onAdd();
                        }
                    }
                }
            };

            searchButton.addActionListener(e -> onSearch());
            addButton.addActionListener(e -> onAdd());

            nickTextField.addKeyListener(keyadapter);
            firstNameTextField.addKeyListener(keyadapter);
            lastNameTextField.addKeyListener(keyadapter);
            resultsTable.addKeyListener(keyadapter);
        }

        private void onSearch() {
            String nick = nickTextField.getText();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            //User&Messages

            List<String> list = new ArrayList<>();
            try {
                list = server.getOnlineUsers();        //showing all users online but where?
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        private void onAdd() {

            //who to add?
            //User&Messages
            //Add

//            try {
//                server.addContact(user,contact);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
        }
    }

    public class ConversationTab extends JPanel {
        private JTextArea conversationTextArea;
        private JLabel usersLabel;
        private JPanel panel;
        private JButton closeButton;
        private JTextField messageTextField;
        private JButton sendButton;
        private List<String> contacts; //Chosen from JList
        private SimpleDateFormat simpleDateFormat;

        public ConversationTab() {
            this.setLayout(new GridLayout());
            this.add(panel);
            this.simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

            sendButton.addActionListener(e -> sendMessage());
            closeButton.addActionListener(e -> closeTab());

            messageTextField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == '\n')
                        sendMessage();
                }
            });
        }

        private void closeTab() {
            tabbedPane.remove(this);
        }

        private void sendMessage() {
            String msg = simpleDateFormat.format(new Date()) + " # " + messageTextField.getText();
            messageTextField.setText("");
            conversationTextArea.append(msg + "\n");

            //User&Messages
            //server.retrieveMessage or sth
        //    try {
        //        server.sendMessage(msg,date,user,....);
        //    }
        }

        public void setLabelText(String text) {
            this.usersLabel.setText(text);
        }

        public List<String> getContacts() {
            return contacts;
        }

        public void setContacts(List<String> contacts) {
            this.contacts = contacts;
        }
    }

    public class Client extends UnicastRemoteObject implements ClientInterface {

        private static final long serialVersionUID = 1L;
        private ServerInterface server;

        public Client(ServerInterface server) throws RemoteException {
            this.server = server;
        }

        @Override
        public Message retreiveMessage(Message message) throws RemoteException {
            return message;
        }

        public ServerInterface getServer() {
            return server;
        }

        public void setServer(ServerInterface server) {
            this.server = server;
        }

    }
}
