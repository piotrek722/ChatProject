package agh.client.gui.searchdialog;

import agh.client.gui.searchdialog.events.AddContactEvent;
import agh.client.gui.searchdialog.events.SearchEvent;
import agh.model.simple.SimplifiedUser;
import agh.router.DefaultEventDispatcher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class SearchDialog extends JDialog {
    private JPanel contentPane;
    private JTextField nickTextField;
    private JTextField lastNameTextField;
    private JTextField firstNameTextField;
    private JButton searchButton;
    private JButton addButton;
    private JTable resultsTable;
    private DefaultTableModel model;

    private SimplifiedUser user;
    private DefaultEventDispatcher dispatcher;

    private static final int SEARCH_WIDTH = 500;
    private static final int SEARCH_HEIGHT = 200;

    public SearchDialog(DefaultEventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.model = new DefaultTableModel();
        setContentPane(contentPane);
        setModal(true);
        setSize(SEARCH_WIDTH, SEARCH_HEIGHT);
        setMinimumSize(new Dimension(SEARCH_WIDTH, SEARCH_HEIGHT));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        KeyAdapter keyadapter = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    Object source = e.getSource();
                    if (source == nickTextField || source == firstNameTextField || source == lastNameTextField) {
                        onSearch();
                    } else if(source == resultsTable) {
                        onAdd();
                    }
                }
            }
        };

        searchButton.addActionListener(e -> onSearch());
        addButton.addActionListener(e -> onAdd());

        nickTextField.addKeyListener(keyadapter);
        firstNameTextField.addKeyListener(keyadapter);
        lastNameTextField.addKeyListener(keyadapter);
        resultsTable.addKeyListener(keyadapter);
    }

    private void createUIComponents() {
        Object[] columnNames = {"Nick", "First Name", "Last Name"};
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames);
        resultsTable = new JTable(model);
    }

    private void onSearch() {
        String nick = nickTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();

        dispatcher.dispatch(new SearchEvent(nick, firstName, lastName));
    }

    private void onAdd() {
        int selectedRow = resultsTable.getSelectedRow();
        String loginToAdd = (String) resultsTable.getValueAt(selectedRow, 0);
        String fname = (String) resultsTable.getValueAt(selectedRow, 1);
        String lname = (String) resultsTable.getValueAt(selectedRow, 2);
        dispatcher.dispatch(new AddContactEvent(user.getLogin(), new SimplifiedUser(loginToAdd, fname, lname)));
    }

    public void displayResultsOfSearch(List<SimplifiedUser> users) {
        clearJTable();
        for (SimplifiedUser user : users) {
            Object[] row = {user.getLogin(), user.getFirstName(), user.getLastName()};
            model.addRow(row);
        }
    }

    public void setUser(SimplifiedUser user) {
        this.user = user;
    }

    public void clearJTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
        }
    }

    public void clearDialog() {
        clearJTable();
        nickTextField.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
    }
}