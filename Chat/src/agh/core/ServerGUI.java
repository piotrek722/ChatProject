package agh.core;

import javax.swing.*;

public class ServerGUI extends JFrame {
    private JPanel mainPanel;
    private JButton sendButton;
    private JTextField textField;
    private JList userList;
    private JTextArea textArea;

    public ServerGUI() {
        super("CHAT SERVER");
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(600, 400);
        setVisible(true);
    }

    public void append(String msg) {
        textArea.append(msg + "\n");
    }

}
