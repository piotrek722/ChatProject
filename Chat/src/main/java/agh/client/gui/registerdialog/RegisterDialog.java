package agh.client.gui.registerdialog;

import agh.client.gui.registerdialog.events.RegisterEvent;
import agh.client.gui.registerdialog.events.SwitchRegisterToLoginEvent;
import agh.router.DefaultEventDispatcher;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

    private static final int REGISTER_WIDTH = 350;
    private static final int REGISTER_HEIGHT = 310;

    public RegisterDialog(DefaultEventDispatcher dispatcher) throws UnsupportedLookAndFeelException {
        this.dispatcher = dispatcher;
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        setContentPane(contentPane);
        setModal(true);
        setSize(REGISTER_WIDTH, REGISTER_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        KeyAdapter keyadapter = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    onSignUp();
                }
            }
        };

        signUpButton.addActionListener(e -> onSignUp());
        signInButton.addActionListener(e -> onSignIn());

        loginTextField.addKeyListener(keyadapter);
        passwordTextField.addKeyListener(keyadapter);
        firstNameTextField.addKeyListener(keyadapter);
        lastNameTextField.addKeyListener(keyadapter);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
    }

    private void onSignUp() {
        String login = loginTextField.getText();
        String password = String.valueOf(passwordTextField.getPassword());
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();

        dispatcher.dispatch(new RegisterEvent(login, firstName, lastName, password));
    }

    public void registeringFailed() {
        serverMsgLabel.setText("Registering failed");
        serverMsgLabel.setForeground(Color.RED);
    }

    public void registeringFailed(String message) {
        serverMsgLabel.setText("Registering failed: " + message);
        serverMsgLabel.setForeground(Color.RED);
    }

    private void onSignIn() {
        dispatcher.dispatch(new SwitchRegisterToLoginEvent());
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
