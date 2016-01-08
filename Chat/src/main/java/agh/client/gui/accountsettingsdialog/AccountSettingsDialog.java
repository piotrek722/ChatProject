package agh.client.gui.accountsettingsdialog;

import agh.client.gui.accountsettingsdialog.events.ChangePasswordEvent;
import agh.client.gui.accountsettingsdialog.events.SaveAccountChangesEvent;
import agh.model.simple.Contact;
import agh.router.EventDispatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AccountSettingsDialog extends JDialog {
    private JPanel contentPane;
    private JTextField loginField;
    private JTextField fnameField;
    private JTextField lnameField;
    private JButton saveChangesButton;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JButton changePasswordButton;
    private JLabel profileLabel;
    private JLabel passwordLabel;

    private Contact user;
    private EventDispatcher dispatcher;

    private final int ACCOUNT_SETTINGS_WIDTCH = 400;
    private final int ACCOUNT_SETTINGS_HEIGHT = 350;

    public AccountSettingsDialog(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        setContentPane(contentPane);
        setModal(true);
        setSize(ACCOUNT_SETTINGS_WIDTCH, ACCOUNT_SETTINGS_HEIGHT);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        saveChangesButton.addActionListener(e -> onSaveChanges());
        changePasswordButton.addActionListener(e -> onChangePassword());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clearDialog();
                super.windowClosing(e);
            }
        });
    }

    private void onSaveChanges() {
        String login = loginField.getText();
        String fname = fnameField.getText();
        String lname = lnameField.getText();

        dispatcher.dispatch(new SaveAccountChangesEvent(login, fname, lname));
    }

    private void onChangePassword() {
        String oldPassword = currentPasswordField.getPassword().toString();
        String newPassword = newPasswordField.getPassword().toString();

        dispatcher.dispatch(new ChangePasswordEvent(oldPassword, newPassword));
    }

    public void displayChangesSavedSuccessfully() {
        profileLabel.setText("Changes saved successfully");
        profileLabel.setForeground(Color.GREEN);
    }

    public void setUser(Contact user) {
        this.user = user;
        setFields();
    }

    private void setFields() {
        this.loginField.setText(this.user.getLogin());
        this.fnameField.setText(this.user.getFirstName());
        this.lnameField.setText(this.user.getLastName());
    }

    public void displaySavingChangesFailed() {
        profileLabel.setText("Saving changes failed");
        profileLabel.setForeground(Color.RED);
    }

    public void displayPasswordChangedSuccessfully() {
        passwordLabel.setText("Password changes successfully");
        passwordLabel.setForeground(Color.GREEN);
    }

    public void displayChangingPasswordFailed() {
        passwordLabel.setText("Changing password failed");
        passwordLabel.setForeground(Color.RED);
    }

    private void clearDialog() {
        profileLabel.setText("");
        passwordLabel.setText("");
        fnameField.setText("");
        lnameField.setText("");
        currentPasswordField.setText("");
        newPasswordField.setText("");
    }
}
