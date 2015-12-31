package agh.client.gui;

import agh.client.events.LoginEvent;
import agh.client.events.SwitchLoginToRegisterEvent;
import agh.router.DefaultEventDispatcher;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

public class LoginDialog extends JDialog {
    private JPanel contentPane;
    private JButton signInButton;
    private JTextField loginTextField;
    private JPasswordField passwordTextField;
    private JButton signUpButton;
    private JLabel serverMsgLabel;

    private DefaultEventDispatcher dispatcher;

    private static final int LOGIN_WIDTH = 300;
    private static final int LOGIN_HEIGHT = 300;

    public LoginDialog(DefaultEventDispatcher dispatcher) throws UnsupportedLookAndFeelException {
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
        String password = passwordTextField.getPassword().toString();

        dispatcher.dispatch(new LoginEvent(login, password));
    }

    private void onSignUp() {
        dispatcher.dispatch(new SwitchLoginToRegisterEvent());
    }

    public void setServerMsgLabelText(String text) {
        serverMsgLabel.setText(text);
        serverMsgLabel.setForeground(Color.RED);
    }

    public void clearPasswordField() {
        passwordTextField.setText("");
    }

    public void clearDialog() {
        loginTextField.setText("");
        passwordTextField.setText("");
    }
}
