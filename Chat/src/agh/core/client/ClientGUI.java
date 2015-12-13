package agh.core.client;

import agh.core.server.IServer;

import javax.swing.*;
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
    private JButton sendButton;
    private JTextField textField;
    private JTabbedPane tabbedPane;
    private JTextArea textAreaStart;
    private SimpleDateFormat simpleDateFormat;

    private IServer server;
    private Client client;

    public ClientGUI(IServer server) throws RemoteException {
        super("CHAT");
        this.server = server;
        //this.client = new Client();
        this.simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        //MENU BAR
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("Menu");
        file.add("Add contact");
        file.add("Account Settings");
        file.addSeparator();
        JMenuItem exit = new JMenuItem("Exit");
        file.add(exit);

        menuBar.add(file);

        this.setJMenuBar(menuBar);


        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') sendMessage();
            }
        });

        //For tests only
        contacts.addElement("Kontakt");
        contacts.addElement("ASDASD");
        contacts.addElement("HTHTth");
        contacts.addElement("jistjoisetu8");
        contacts.addElement("jifosejfisoe");

        /**
         * Double click on contact from the JList adds ConversationTab to JTabbedPane
         * with the contact name and set it as selected.
         * If such tab already exists just set it as selected.
         * DELETE
         */
        contactJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JList theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        if((index = tabbedPane.indexOfTab(o.toString())) == -1) {
                            ConversationTab conversationTab = new ConversationTab();
                            tabbedPane.addTab(o.toString(), conversationTab);
                            tabbedPane.setSelectedComponent(conversationTab);
                        }
                        else
                            tabbedPane.setSelectedIndex(index);
                    }
                }
            }
        });

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


    private void sendMessage() {
        //Here we should create object Message instead of String
        String msg = simpleDateFormat.format(new Date()) + " # " + textField.getText();
        textField.setText("");
        ConversationTab tab = (ConversationTab) tabbedPane.getSelectedComponent();
        tab.append(msg); //append to client's conversation textarea

        ArrayList<String> users = tab.getContacts(); //to Message object

        try {
            server.retrieveMessage(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

        //If it's not
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

        //TEMPORARY
        @Override
        public void retrieveMessage(String message) throws RemoteException {
            textAreaStart.append(message + "\n");
        }

        //TEMPORARY?
        public String getLogin() {
            return login;
        }
    }

    public class Login extends JDialog {
        private JPanel contentPane;
        private JButton buttonSignIn;
        private JTextField textFieldLogin;
        private JPasswordField passwordField;
        private JButton buttonSignUp;

        public Login() {
            setContentPane(contentPane);
            setModal(true);
            getRootPane().setDefaultButton(buttonSignIn);
            setSize(300, 300);
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            buttonSignIn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onSignIn();
                }
            });

            buttonSignUp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onSignUp();
                }
            });
        }

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

    public class Register extends JDialog {
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
            getRootPane().setDefaultButton(buttonSignUp);
            setSize(400, 400);
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            buttonSignUp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onSignUp();
                }
            });

            buttonSignIn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onSignIn();
                }
            });
        }

        private void onSignUp() {
            //dispose();
        }

        private void onSignIn() {
            dispose();
            new Login().setVisible(true);
        }
    }

    public class ConversationTab extends JPanel{
        private JTextArea textArea;
        private JLabel label;
        private JPanel panel;
        private JButton closeButton;

        public ConversationTab() {
            this.setLayout(new GridLayout());
            this.add(panel);
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    closeTab();
                }
            });
        }

        private ArrayList<String> contacts; //Chosen from JList

        public void append(String message) {
            this.textArea.append(message + "\n");
        }

        private void closeTab() {
            tabbedPane.remove(this);
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
