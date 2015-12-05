package agh.core.client;

import agh.core.server.IServer;
import javax.swing.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientGUI extends JFrame  {
    private JPanel mainPanel;
    private JList userList;
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
        this.client = new Client();

        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSend();
            }
        });

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n')  onSend();
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    server.unregisterClient(client);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }));

        setSize(600, 400);
        setVisible(true);
    }

    private void onSend() {

        String msg = simpleDateFormat.format(new Date()) + " " + textField.getText();
        textField.setText("");
        textAreaStart.append(msg);
        try {
            server.retrieveMessage(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public class Client extends UnicastRemoteObject implements IClient{
        private static final long serialVersionUID = 1L;
        public Client() throws RemoteException {
            server.registerClient(this);
        }

        @Override
        public void retrieveMessage(String message) throws RemoteException {
            textAreaStart.append(message + "\n");
        }
    }
}
