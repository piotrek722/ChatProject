package agh.client.gui.mainframe;

import agh.client.gui.conversationframe.ConversationFrame;
import agh.client.gui.mainframe.events.*;
import agh.model.simple.ClientMessage;
import agh.model.simple.SimplifiedUser;
import agh.router.DefaultEventDispatcher;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JList contactJList;
    private DefaultListModel<SimplifiedUser> contacts;
    private JTabbedPane tabbedPane;
    private JPopupMenu popupMenu;

    private String userLogin;
    private SimplifiedUser user;

    private DefaultEventDispatcher dispatcher;
    private Map<Integer, ConversationFrame> conversationFrames;

    private static final Logger LOGGER = Logger.getLogger("Logger");
    private static final int CLIENT_WIDTH = 200;
    private static final int CLIENT_HEIGHT = 500;

    public MainFrame(DefaultEventDispatcher dispatcher) throws UnsupportedLookAndFeelException {
        super("Chat");
        this.dispatcher = dispatcher;
        this.conversationFrames = new HashMap<>();
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
                    openConversationWindow();
                } else if (e.getKeyChar() == KeyEvent.VK_DELETE) {
                    onDeleteContact();
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onExit();
                super.windowClosing(e);
            }
        });
    }

    private void createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Menu");

        JMenuItem searchContact = new JMenuItem("Search contact");
        searchContact.addActionListener(e ->  onSearch());
        searchContact.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
        file.add(searchContact);

        JMenuItem accountSettings = new JMenuItem("Account Settings");
        accountSettings.addActionListener(e -> onAccountSettings());
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
        startConversation.addActionListener(e -> openConversationWindow());
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

    public void setUser(SimplifiedUser user) {
        this.user = user;
    }

    public void setContacts(List<SimplifiedUser> contacts) {
        this.contacts.removeAllElements();
        for (SimplifiedUser contact : contacts) {
            this.contacts.addElement(contact);
        }
        sortJlist();
    }

    private void sortJlist() {
        Object[] elems = contacts.toArray();
        contacts.removeAllElements();

        Arrays.sort(elems);
        for (Object elem : elems) {
            contacts.addElement((SimplifiedUser) elem);
        }
    }

    private void onAccountSettings() {
        dispatcher.dispatch(new ShowAccountSettingsEvent(user));
    }

    private void onSearch() {
        dispatcher.dispatch(new ShowSearchEvent());
    }

    private void onDeleteContact() {
        List<String> values = contactJList.getSelectedValuesList();
        dispatcher.dispatch(new DeleteContactsEvent(user.getLogin(), values));
    }

    private void onLogOut() {
        dispatcher.dispatch(new LogoutEvent(user.getLogin()));
    }

    private void onExit() {
        dispatcher.dispatch(new CloseEvent(user.getLogin()));
    }

    public void addContact(SimplifiedUser contact) {
        contacts.addElement(contact);
        sortJlist();
    }

    public void deleteContacts(List<SimplifiedUser> contacts) {
        for (SimplifiedUser contact : contacts) {
            this.contacts.removeElement(contact);
        }
    }

    public void displayMessage(ClientMessage message) {
        conversationFrames.get(message.getReceivers().hashCode()).displayMessage(message);
    }

    private void openConversationWindow() {
        List<SimplifiedUser> selectedContacts = new ArrayList<>();

        for (int index : contactJList.getSelectedIndices()) {
            selectedContacts.add(contacts.elementAt(index));
        }

        for (ConversationFrame window : conversationFrames.values()) {
            List<SimplifiedUser> windowContactList = window.getParticipants();
            if (Arrays.equals(windowContactList.toArray(), selectedContacts.toArray())) {
                if (!window.isVisible()) {
                    window.setVisible(true);
                }
                window.toFront();
                return;
            }
        }

        try {
            //conversationFrames.add(new ConversationFrame(user, selectedContacts, dispatcher));
            conversationFrames.put(selectedContacts.hashCode(), new ConversationFrame(user, selectedContacts, dispatcher));
        } catch (UnsupportedLookAndFeelException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }

    public void clearFrame() {
        contacts.removeAllElements();
        conversationFrames.clear();
        userLogin = null;
    }
}
