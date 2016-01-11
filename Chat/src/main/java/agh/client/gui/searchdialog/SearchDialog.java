package agh.client.gui.searchdialog;

import agh.client.gui.searchdialog.events.AddContactEvent;
import agh.client.gui.searchdialog.events.SearchEvent;
import agh.model.simple.SimplifiedUser;
import agh.model.simple.SimplifiedUserList;
import agh.router.DefaultEventDispatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SearchDialog extends JDialog {
    private JPanel contentPane;
    private JTextField nickTextField;
    private JTextField lastNameTextField;
    private JTextField firstNameTextField;
    private JButton searchButton;
    private JButton addButton;
    private JList resultList;
    private DefaultListModel<SimplifiedUser> model;

    private SimplifiedUser user;
    private DefaultEventDispatcher dispatcher;

    private static final int SEARCH_WIDTH = 500;
    private static final int SEARCH_HEIGHT = 200;

    public SearchDialog(DefaultEventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
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
                    } else if(source == resultList) {
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
        resultList.addKeyListener(keyadapter);
    }

    private void createUIComponents() {
        this.model = new DefaultListModel();
        resultList = new JList(model);
        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void onSearch() {
        String nick = nickTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();

        dispatcher.dispatch(new SearchEvent(nick, firstName, lastName));
    }

    private void onAdd() {
        int selectedIndex = resultList.getSelectedIndex();

        if (selectedIndex >= 0) {
            dispatcher.dispatch(new AddContactEvent(user.getLogin(), model.elementAt(selectedIndex)));
        }
    }

    public void displayResultsOfSearch(SimplifiedUserList users) {
        clearJTable();
        users.stream().forEach(user -> model.addElement(user));
    }

    public void setUser(SimplifiedUser user) {
        this.user = user;
    }

    public void clearJTable() {
        model.removeAllElements();
    }

    public void clearDialog() {
        clearJTable();
        nickTextField.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
    }
}