package agh.core.server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerGUI extends JFrame {
    private JPanel mainPanel;
    private JButton sendButton;
    private JTextField textField;
    private JList userList;
    private DefaultListModel<String> userOnlineList; //Later DefaultListModel<User>
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

    public void append(String msg) {
        textArea.append(msg + "\n");
    }

    public void addClientToJList(String username) {
        userOnlineList.addElement(username);
    }

    private void onSend() {
        String servermsg = "SERVER> " + textField.getText();
        textField.setText("");
        append(servermsg);
        //server.broadcast(servermsg);
    }

    public Server getServer() {
        return server;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        userOnlineList = new DefaultListModel<>();
        userList = new JList(userOnlineList);
    }

    public class Server extends UnicastRemoteObject implements IServer {
        private static final long serialVersionUID = 1L;
        //List<Clients>
        public Server() throws RemoteException {

        }

    }
}
