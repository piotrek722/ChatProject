package agh.core.server;

import agh.core.client.IClient;
import javax.swing.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerGUI extends JFrame {
    private JPanel mainPanel;
    private JButton sendButton;
    private JTextField textField;
    private JList contactJList;
    private DefaultListModel<String> contacts; //Later DefaultListModel<User>
    private JTextArea textArea;

    private Server server;

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

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') onSend();
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        setSize(600, 400);
        setVisible(true);
    }

    private void onSend() {
        String servermsg = "SERVER> " + textField.getText();
        textField.setText("");
        textArea.append(servermsg + "\n");
        try {
            server.broadcastMessage(servermsg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Server getServer() {
        return server;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        contacts = new DefaultListModel<>();
        contactJList = new JList(contacts);
    }

    public class Server extends UnicastRemoteObject implements IServer {
        private static final long serialVersionUID = 1L;
        ArrayList<IClient> clients;

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
            //registerClient();
            return true;
        }
    }
}
