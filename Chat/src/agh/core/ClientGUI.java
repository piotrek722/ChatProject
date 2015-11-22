package agh.core;

import javax.swing.*;

public class ClientGUI extends JFrame  {
    private JPanel mainPanel;
    private JList userList;
    private JButton sendButton;
    private JTextField textField;
    private JTabbedPane tabbedPane1;
    private JTextArea textAreaStart;

    private Client client;

    public ClientGUI(Client client) {
        super("CHAT");
        this.client = client;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);

        setSize(600, 400);
        setVisible(true);
    }

    public void append(String msg) {
        textAreaStart.append(msg);
    }
}
