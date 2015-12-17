package agh.core.server;

import agh.core.client.IClient;
import javax.swing.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class ServerGUI extends JFrame {
    private JPanel mainPanel;
    private JButton sendButton;
    private JTextField textField;
    private JList contactJList;
    private DefaultListModel<String> contacts;
    private JTextArea textArea;

    private Server server;

    private static final Logger LOGGER = Logger.getLogger("Logger");
    private static final int SERVER_WIDTH = 600;
    private static final int SERVER_HEIGHT = 400;

    public ServerGUI() throws RemoteException {
        super("CHAT SERVER");
        server = new Server();
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSend();
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    onSend();
                }
            }
        });

        setSize(SERVER_WIDTH, SERVER_HEIGHT);
        setVisible(true);
    }

    private void onSend() {
        String servermsg = "SERVER> " + textField.getText();
        textField.setText("");
        textArea.append(servermsg + "\n");
        try {
            server.broadcastMessage(servermsg);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public Server getServer() {
        return server;
    }

    private void createUIComponents() {
        contacts = new DefaultListModel<>();
        contactJList = new JList(contacts);
    }

    public class Server extends UnicastRemoteObject implements IServer {
        private static final long serialVersionUID = 1L;
        List<IClient> clients;

        public Server() throws RemoteException {
            clients = new ArrayList<>();
        }

        //Temporary for CORE tests
        @Override
        public void registerClient(IClient client) throws RemoteException {
            this.clients.add(client);
            contacts.addElement(client.getLogin());
        }

        @Override
        public void unregisterClient(IClient client) throws RemoteException {
            this.clients.remove(client);
            contacts.removeElement(client.getLogin());
        }

        @Override
        public void broadcastMessage(String message) throws RemoteException {
            for (IClient client : clients) {
                client.retrieveMessage(message);
            }
        }

        @Override
        public void retrieveMessage(String message) throws RemoteException {
            textArea.append(message + "\n");
        }

        @Override
        public Boolean login(String name, String password) throws RemoteException {
            return true;
        }
    }
}
