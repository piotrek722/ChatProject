package agh.core.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JDialog {
    private JPanel contentPane;
    private JButton buttonSignIn;
    private JTextField textFieldLogin;
    private JPasswordField passwordField;
    private JButton buttonSignUp;

    public Login() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonSignIn);
        setSize(300,300);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        buttonSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSignIn();
            }
        });

        buttonSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSignUp();
            }
        });
    }

    private void onSignIn() {
// add your code here
        dispose();
    }

    private void onSignUp() {
        dispose();
        new Register().setVisible(true);
    }

    public static void main(String[] args) {
        Login dialog = new Login();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
