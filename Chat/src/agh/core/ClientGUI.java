package agh.core;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientGUI extends JFrame  {
    private JPanel mainPanel;
    private JList userList;
    private JButton sendButton;
    private JTextField textField;
    private JTabbedPane tabbedPane1;
    private JTextArea textAreaStart;
    private SimpleDateFormat simpleDateFormat;

    private Client client;

    public ClientGUI() {
        super("CHAT");
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);

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
    /**
     * Method appends message to the textArea
     * @param msg Message to append
     */
    public void append(String msg) {
        //String time = simpleDateFormat.format(new Date());
        textAreaStart.append(msg + "\n");
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Method sends message which is in textField to server.
     */
    private void onSend() {
        String msg = textField.getText();
        textField.setText("");
        textAreaStart.append(msg);
        client.sendMessage(msg);
    }
}
