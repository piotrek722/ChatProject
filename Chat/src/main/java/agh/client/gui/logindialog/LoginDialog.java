package agh.client.gui.logindialog;

import agh.client.gui.logindialog.events.LoginEvent;
import agh.client.gui.logindialog.events.SwitchLoginToRegisterEvent;
import agh.router.DefaultEventDispatcher;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginDialog extends JDialog {
    private JPanel contentPane;
    private JButton signInButton;
    private JTextField loginTextField;
    private JPasswordField passwordTextField;
    private JButton signUpButton;
    private JLabel serverMsgLabel;

    private DefaultEventDispatcher dispatcher;

    private static final int LOGIN_WIDTH = 300;
    private static final int LOGIN_HEIGHT = 260;

    public LoginDialog(DefaultEventDispatcher dispatcher) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        this.dispatcher = dispatcher;
        setContentPane(contentPane);
        setModal(true);
        setSize(LOGIN_WIDTH, LOGIN_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clearDialog();
                super.windowClosing(e);
            }
        });
    }

    private void onSignIn() {
        String login = loginTextField.getText();
        String password = String.valueOf(passwordTextField.getPassword());

        dispatcher.dispatch(new LoginEvent(login, password));
    }

    public void registeredSuccessfully() {
        serverMsgLabel.setText("Registered successfully. Log in.");
        serverMsgLabel.setForeground(Color.GREEN);
    }

    public void loggingInFailed() {
        serverMsgLabel.setText("Logging in failed");
        serverMsgLabel.setForeground(Color.RED);
    }

    public void loggingInFailed(String message) {
        serverMsgLabel.setText("Logging in failed: " + message);
        serverMsgLabel.setForeground(Color.RED);
    }

    private void onSignUp() {
        dispatcher.dispatch(new SwitchLoginToRegisterEvent());
    }

    public void clearPasswordField() {
        passwordTextField.setText("");
    }

    public void clearDialog() {
        loginTextField.setText("");
        passwordTextField.setText("");
        serverMsgLabel.setText("");
    }
}
