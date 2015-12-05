package agh.core.client;

import agh.core.server.IServer;

import javax.swing.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;

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
        client = new Client();
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

        setSize(600, 400);
        setVisible(true);
    }

    public void append(String msg) {
        //String time = simpleDateFormat.format(new Date());
        textAreaStart.append(msg + "\n");
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private void onSend() {
        String msg = textField.getText();
        textField.setText("");
        textAreaStart.append(msg);
        //client.sendMessage(msg);
    }

    public class Client extends UnicastRemoteObject implements IClient{
        private static final long serialVersionUID = 1L;
        protected Client() throws RemoteException {

        }

    }
}
