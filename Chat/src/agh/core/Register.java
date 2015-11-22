package agh.core;

import javax.swing.*;
import java.awt.event.*;

public class Register extends JDialog {
    private JPanel contentPane;
    private JButton buttonSignUp;
    private JButton buttonSignIn;
    private JTextField textFieldLogin;
    private JPasswordField passwordField;
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;

    public Register() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonSignUp);
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        buttonSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSignUp();
            }
        });

        buttonSignIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSignIn();
            }
        });

// call onSignIn() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onSignIn();
            }
        });

// call onSignIn() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSignIn();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onSignUp() {
        //dispose();
    }

    private void onSignIn() {
        dispose();
        new Login().setVisible(true);
    }

    public static void main(String[] args) {
        Register dialog = new Register();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
