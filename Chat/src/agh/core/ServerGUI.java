package agh.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ServerGUI extends JFrame {
    private JPanel mainPanel;
    private JButton broadcastButton;
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

}
