package agh.clientgui;

import agh.server.IServer;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class ClientGUI extends JFrame {
    private JPanel mainPanel;
    private JList contactJList;
    private DefaultListModel<String> contacts;
    private JTabbedPane tabbedPane;
    private JPopupMenu popupMenu;

    private static final Logger LOGGER = Logger.getLogger("Logger");
    private static final int CLIENT_WIDTH = 600;
    private static final int CLIENT_HEIGHT = 400;

    public ClientGUI(final IServer server) throws RemoteException, UnsupportedLookAndFeelException {
        super("Chat");
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        setContentPane(mainPanel);
        setSize(CLIENT_WIDTH, CLIENT_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createMenuBar();
        createPopupMenu();

        /*contacts.addElement("Adrian Puchacki");
        contacts.addElement("Jan Paweł");
        contacts.addElement("Jan Paweł 2");
        contacts.addElement("Kuba Nowicki");
        contacts.addElement("Waldemar Walasik");
        contacts.addElement("Wojciech Puczyk");*/

        contactJList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    startConversationWithChosenContacts();
                } else if (e.getKeyChar() == KeyEvent.VK_DELETE) {
                    onDeleteContact();
                }
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //User&Messages
            //Unregister Client
        }));

        new Login().setVisible(true);
        setVisible(true);
    }

    private void createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Menu");

        JMenuItem searchContact = new JMenuItem("Search contact");
        searchContact.addActionListener(e ->  new Search().setVisible(true));
        searchContact.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
        file.add(searchContact);

        JMenuItem accountSettings = new JMenuItem("Account Settings");
        //ACTIONLISTENER
        accountSettings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        file.add(accountSettings);

        file.addSeparator();

        JMenuItem logout = new JMenuItem("Log out");
        logout.addActionListener(e -> onLogOut());
        logout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        file.add(logout);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> onExit());
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
        file.add(exit);

        menuBar.add(file);
        this.setJMenuBar(menuBar);
    }

    private void createPopupMenu(){
        this.popupMenu = new JPopupMenu();

        JMenuItem startConversation = new JMenuItem("Start conversation");
        startConversation.addActionListener(e -> startConversationWithChosenContacts());
        popupMenu.add(startConversation);

        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(e -> onDeleteContact());
        popupMenu.add(delete);

        contactJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = contactJList.locationToIndex(new Point(e.getX(), e.getY()));
                    if (!contactJList.isSelectedIndex(index)) {
                        contactJList.setSelectedIndex(index);
                    }
                    popupMenu.show(contactJList, e.getX(), e.getY());
                }
            }
        });
    }

    private void createUIComponents() {
        contacts = new DefaultListModel<>();
        contactJList = new JList(contacts);
    }

    private void onExit() {
        onLogOut();
        //Exit
    }

    private void onLogOut() {
        //Logout
    }

    private void onDeleteContact() {
        List<Object> values = contactJList.getSelectedValuesList();
        for (Object elem : values) {
            contacts.removeElement(elem);
        }
    }

    private void startConversationWithChosenContacts() {
        List<String> chosenContacts = new ArrayList<>();
        String text = "";

        for (int index : contactJList.getSelectedIndices()) {
            chosenContacts.add(contacts.elementAt(index));
            text += contacts.elementAt(index) + "; ";
        }

        ConversationTab conversationTab = new ConversationTab(); //Component for Tab
        conversationTab.setContacts(chosenContacts); //Contacts for conversation tab
        conversationTab.setLabelText(text); //List of contacts for conversation tab

        //Check if the conversationTab is already opened
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            List<String> conversationContactsList;
            conversationContactsList = ((ConversationTab)tabbedPane.getComponentAt(i)).getContacts();
            if (Arrays.equals(chosenContacts.toArray(), conversationContactsList.toArray())) {
                tabbedPane.setSelectedIndex(i);
                return;
            }
        }

        //It is not opened
        if (chosenContacts.size() == 1) {
            tabbedPane.addTab(chosenContacts.get(0), conversationTab);
            tabbedPane.setSelectedComponent(conversationTab);
        } else if (chosenContacts.size() > 1){
            tabbedPane.addTab("Conference", conversationTab);
            tabbedPane.setSelectedComponent(conversationTab);
        }
    }
}
