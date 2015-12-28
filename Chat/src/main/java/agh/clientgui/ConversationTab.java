package agh.clientgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConversationTab extends JPanel {
    private JTextArea conversationTextArea;
    private JLabel usersLabel;
    private JPanel panel;
    private JButton closeButton;
    private JTextField messageTextField;
    private JButton sendButton;
    private List<String> contacts; //Chosen from JList
    private SimpleDateFormat simpleDateFormat;

    public ConversationTab() {
        this.setLayout(new GridLayout());
        this.add(panel);
        this.simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        sendButton.addActionListener(e -> sendMessage());
        closeButton.addActionListener(e -> closeTab());

        messageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    sendMessage();
            }
        });
    }

    private void closeTab() {
        //tabbedPane.remove(this);
    }

    private void sendMessage() {
        String msg = simpleDateFormat.format(new Date()) + " # " + messageTextField.getText();
        messageTextField.setText("");
        conversationTextArea.append(msg + "\n");

        //User&Messages
        //server.retrieveMessage or sth
    }

    public void setLabelText(String text) {
        this.usersLabel.setText(text);
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }
}
