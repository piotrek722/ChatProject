package agh.core.client;

import agh.core.server.IServer;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClientGUI extends JFrame {
    private JPanel mainPanel;
    private JList contactJList;
    private DefaultListModel<String> contacts;
    private JTabbedPane tabbedPane;

    private IServer server;
    private Client client;

    public ClientGUI(IServer server) throws RemoteException, UnsupportedLookAndFeelException {
        super("CHAT");
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        this.server = server;

        //MENU BAR
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("Menu");
        JMenuItem addContact = new JMenuItem("Add contact");
        addContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Search().setVisible(true);
            }
        });
        file.add(addContact);
        JMenuItem accountSettings = new JMenuItem("Account Settings");
        file.add(accountSettings);
        file.addSeparator();
        JMenuItem exit = new JMenuItem("Exit");
        file.add(exit);

        menuBar.add(file);

        this.setJMenuBar(menuBar);
        //MENU BAR

        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //For tests only
        contacts.addElement("Kontakt");
        contacts.addElement("ASDASD");
        contacts.addElement("HTHTth");
        contacts.addElement("jistjoisetu8");
        contacts.addElement("jifosejfisoe");

        contactJList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    startConversationWithChosenContacts();
                }
            }
        });

        /**
         * On close send to server that client is offline
         */
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    server.unregisterClient(client);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }));


        new Login().setVisible(true);

        setSize(600, 400);
        setVisible(true);
    }


    private void startConversationWithChosenContacts() {
        ArrayList<String> chosenContacts = new ArrayList<>();
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
            ArrayList<String> conversationContactsList;
            conversationContactsList = ((ConversationTab)tabbedPane.getComponentAt(i)).getContacts();
            if (Arrays.equals(chosenContacts.toArray(), conversationContactsList.toArray())) {
                tabbedPane.setSelectedIndex(i);
                return;
            }
        }

        //It is not opened
        if (chosenContacts.size() == 1) //If private conversation
            tabbedPane.addTab(chosenContacts.get(0), conversationTab);
        else //Conference
            tabbedPane.addTab("Conference", conversationTab);

        tabbedPane.setSelectedComponent(conversationTab);
    }

    private void createUIComponents() {
        contacts = new DefaultListModel<>();
        contactJList = new JList(contacts);
    }

    public class Client extends UnicastRemoteObject implements IClient {
        private static final long serialVersionUID = 1L;
        private String login;

        public Client(String login) throws RemoteException {
            this.login = login;
            server.registerClient(this);
        }

        //Temporary for CORE tests
        @Override
        public void retrieveMessage(String message) throws RemoteException {
            //find tab or open new tab
            //append message
        }

        //TEMPORARY?
        public String getLogin() {
            return login;
        }
    }

    public class Login extends JDialog implements ActionListener{
        private JPanel contentPane;
        private JButton buttonSignIn;
        private JTextField textFieldLogin;
        private JPasswordField passwordField;
        private JButton buttonSignUp;

        public Login() {
            setContentPane(contentPane);
            setModal(true);
            setSize(300, 300);
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            buttonSignIn.addActionListener(this);
            buttonSignUp.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src == buttonSignIn) onSignIn();
            else if (src == buttonSignUp) onSignUp();
        }

        //Temporary for CORE tests
        private void onSignIn() {
            String login = textFieldLogin.getText();
            //Password?
            try {
                if(server.login(login, "PASS")){
                    client = new Client(login);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            dispose();
        }

        private void onSignUp() {
            dispose();
            new Register().setVisible(true);
        }
    }

    public class Register extends JDialog implements ActionListener {
        private JPanel contentPane;
        private JButton buttonSignUp;
        private JButton buttonSignIn;
        private JTextField textFieldLogin;
        private JPasswordField passwordField;
        private JTextField textFieldFirstName;
        private JTextField textFieldLastName;

        public Register() {
            setContentPane(contentPane);
            setModal(true);
            setSize(400, 400);
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            buttonSignUp.addActionListener(this);
            buttonSignIn.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src == buttonSignIn) onSignIn();
            else if (src == buttonSignUp) onSignUp();
        }

        private void onSignUp() {
            //dispose();
        }

        private void onSignIn() {
            dispose();
            new Login().setVisible(true);
        }
    }

    public class Search extends JDialog {
        private JPanel contentPane;
        private JTextField nickField;
        private JTextField lastNameField;
        private JTextField firstNameField;
        private JButton searchButton;
        private JButton addButton;
        private JTable resultsTable;
        private JButton buttonOK;

        public Search() {
            setContentPane(contentPane);
            setModal(true);
            setSize(500, 200);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }

    public class ConversationTab extends JPanel implements ActionListener {
        private JTextArea textArea;
        private JLabel label;
        private JPanel panel;
        private JButton closeButton;
        private JTextField messageField;
        private JButton sendButton;
        private ArrayList<String> contacts; //Chosen from JList
        private SimpleDateFormat simpleDateFormat;

        public ConversationTab() {
            this.setLayout(new GridLayout());
            this.add(panel);
            this.simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

            sendButton.addActionListener(this);
            closeButton.addActionListener(this);

            messageField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == '\n')
                        sendMessage();
                }
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src == sendButton) sendMessage();
            else if (src == closeButton) closeTab();
        }

        private void closeTab() {
            tabbedPane.remove(this);
        }

        private void sendMessage() {
            String msg = simpleDateFormat.format(new Date()) + " # " + messageField.getText();
            messageField.setText("");
            textArea.append(msg + "\n");

            try {
                server.retrieveMessage(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void setLabelText(String text) {
            this.label.setText(text);
        }

        public ArrayList<String> getContacts() {
            return contacts;
        }

        public void setContacts(ArrayList<String> contacts) {
            this.contacts = contacts;
        }
    }
}
