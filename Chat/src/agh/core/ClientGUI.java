package agh.core;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame  {
    private JPanel mainPanel;
    private JList userList;
    private JButton sendButton;
    private JTextField textField;
    private JTabbedPane tabbedPane1;
    private JTextArea textAreaStart;

    private Client client;

    public ClientGUI() {
        super("CHAT");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setContentPane(mainPanel);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSend();
            }
        });

        setSize(600, 400);
        setVisible(true);
    }

    public void append(String msg) {
        textAreaStart.append(msg + "\n");
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private void onSend() {
        String msg = textField.getText();
        textField.setText("");
        textAreaStart.append(msg);
        client.sendMessage(msg);
    }
}
