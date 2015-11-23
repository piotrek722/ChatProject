package agh.core;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ServerGUI extends JFrame {
    private JPanel mainPanel;
    private JButton sendButton;
    private JTextField textField;
    private JList userList;
    private DefaultListModel<String> userOnlineList; //Later DefaultListModel<User>
    private JTextArea textArea;

    private Server server;

    public ServerGUI() {
        super("CHAT SERVER");
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

    /**
     * Method appends message to the textArea
     * @param msg Message to append
     */
    public void append(String msg) {
        textArea.append(msg + "\n");
    }

    /**
     * Method adds client, who log in to chat by Client.
     * @param username User name
     */
    public void addClientToJList(String username) {
        userOnlineList.addElement(username);
    }

    /**
     * Method sends message to all online clients
     * and appends it to server text area.
     */
    private void onSend() {
        String servermsg = "SERVER> " + textField.getText();
        textField.setText("");
        append(servermsg);
        server.broadcast(servermsg);
    }

    public void setServer(Server server) {
        this.server = server;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        userOnlineList = new DefaultListModel<>();
        userList = new JList(userOnlineList);
    }
}
