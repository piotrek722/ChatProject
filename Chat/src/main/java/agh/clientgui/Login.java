package agh.clientgui;

import agh.router.EventDispatcher;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

public class Login extends JDialog {
    private JPanel contentPane;
    private JButton signInButton;
    private JTextField loginTextField;
    private JPasswordField passwordTextField;
    private JButton signUpButton;
    private JLabel serverMsgLabel;

    private EventDispatcher dispatcher;

    private static final int LOGIN_WIDTH = 300;
    private static final int LOGIN_HEIGHT = 300;

    public Login(EventDispatcher dispatcher) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        this.dispatcher = dispatcher;
        setContentPane(contentPane);
        setModal(true);
        setSize(LOGIN_WIDTH, LOGIN_HEIGHT);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        signInButton.addActionListener(e -> onSignIn());
        signUpButton.addActionListener(e -> onSignUp());

        KeyAdapter keyadapter = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    onSignIn();
                }
            }
        };

        loginTextField.addKeyListener(keyadapter);
        passwordTextField.addKeyListener(keyadapter);
    }

    private void onSignIn() {
        String login = loginTextField.getText();
        char[] password = passwordTextField.getPassword();

        try {
            new ClientGUI(dispatcher);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        dispose();
    }

    private void onSignUp() {
        dispose();
        try {
            new Register(dispatcher).setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
