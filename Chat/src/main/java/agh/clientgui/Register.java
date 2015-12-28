package agh.clientgui;

import agh.router.EventDispatcher;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

public class Register extends JDialog {
    private JPanel contentPane;
    private JButton signUpButton;
    private JButton signInButton;
    private JTextField loginTextField;
    private JPasswordField passwordTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JLabel serverMsgLabel;

    private EventDispatcher dispatcher;

    private static final int REGISTER_WIDTH = 400;
    private static final int REGISTER_HEIGHT = 400;

    public Register(EventDispatcher dispatcher) throws UnsupportedLookAndFeelException {
        this.dispatcher = dispatcher;
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        setContentPane(contentPane);
        setModal(true);
        setSize(REGISTER_WIDTH, REGISTER_HEIGHT);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        KeyAdapter keyadapter = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    onSignIn();
                }
            }
        };

        signUpButton.addActionListener(e -> onSignUp());
        signInButton.addActionListener(e -> onSignIn());

        loginTextField.addKeyListener(keyadapter);
        passwordTextField.addKeyListener(keyadapter);
        firstNameTextField.addKeyListener(keyadapter);
        lastNameTextField.addKeyListener(keyadapter);
    }

    private void onSignUp() {
        String login = loginTextField.getText();
        char[] password = passwordTextField.getPassword();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();

        try {
            new ClientGUI(dispatcher);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        //User&Messages
        dispose();
    }

    private void onSignIn() {
        dispose();
        try {
            new Login(dispatcher).setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
