package agh.client.gui;

import javax.swing.*;

public class AccountSettingsDialog extends JDialog {
    private JPanel contentPane;
    private JTextField nickField;
    private JTextField fnameField;
    private JTextField lnameField;
    private JButton saveChangesButton;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JButton changePasswordButton;

    private final int ACCOUNT_SETTINGS_WIDTCH = 400;
    private final int ACCOUNT_SETTINGS_HEIGHT = 350;

    public AccountSettingsDialog() {
        setContentPane(contentPane);
        setModal(true);
        setSize(ACCOUNT_SETTINGS_WIDTCH, ACCOUNT_SETTINGS_HEIGHT);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
