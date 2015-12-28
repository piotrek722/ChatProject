package agh.server;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.logging.*;

public class ServerGUI extends JFrame {
    private JPanel mainPanel;
    private JButton sendButton;
    private JTextField textField;
    private JList usersJList;
    private DefaultListModel<String> usersOnline;
    private JTextArea textArea;

    private Server server;

    private static final Logger LOGGER = Logger.getLogger("ServerLogger");
    private static final int SERVER_WIDTH = 600;
    private static final int SERVER_HEIGHT = 400;

    public ServerGUI() throws RemoteException, UnsupportedLookAndFeelException {
        super("Chat Server");
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        setContentPane(mainPanel);
        setSize(SERVER_WIDTH, SERVER_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        server = new Server();

        sendButton.addActionListener(e -> onSend());

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    onSend();
                }
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //User&Messages
            //Server shutdown
        }));

        setVisible(true);
    }

    private void createUIComponents() {
        usersOnline = new DefaultListModel<>();
        usersJList = new JList(usersOnline);
    }

    private void onSend() {
        String servermsg = "SERVER> " + textField.getText();
        textField.setText("");
        textArea.append(servermsg + "\n");

        //User&Messages
    }

    public Server getServer() {
        return server;
    }
}
