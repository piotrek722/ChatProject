package agh.client.gui;

import agh.client.events.RegisterEvent;
import agh.client.events.SwitchLoginToRegisterEvent;
import agh.client.events.SwitchRegisterToLoginEvent;
import agh.router.DefaultEventDispatcher;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

public class RegisterDialog extends JDialog {
    private JPanel contentPane;
    private JButton signUpButton;
    private JButton signInButton;
    private JTextField loginTextField;
    private JPasswordField passwordTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JLabel serverMsgLabel;

    private DefaultEventDispatcher dispatcher;

    private static final int REGISTER_WIDTH = 400;
    private static final int REGISTER_HEIGHT = 400;

    public RegisterDialog(DefaultEventDispatcher dispatcher) throws UnsupportedLookAndFeelException {
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
        String password = passwordTextField.getPassword().toString();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();

        dispatcher.dispatch(new RegisterEvent(login, firstName, lastName, password));
    }

    private void onSignIn() {
        dispatcher.dispatch(new SwitchRegisterToLoginEvent());
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
        firstNameTextField.setText("");
        lastNameTextField.setText("");
    }
}
