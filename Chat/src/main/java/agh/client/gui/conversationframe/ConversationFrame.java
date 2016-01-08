package agh.client.gui.conversationframe;

import agh.client.gui.conversationframe.events.SendMessageEvent;
import agh.router.EventDispatcher;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConversationFrame extends JFrame{
    private JTextArea conversationTextArea;
    private JPanel mainPanel;
    private JTextField messageTextField;
    private JButton sendButton;

    private String userLogin;
    private List<String> users;
    private EventDispatcher dispatcher;
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final int CONVERSATION_FRAME_WIDTH = 400;
    private static final int CONVERSATION_FRAME_HEIGHT = 300;

    public ConversationFrame(String userLogin, List<String> users, EventDispatcher dispatcher) throws UnsupportedLookAndFeelException {
        super(String.join(", ", users));
        this.userLogin = userLogin;
        this.users = users;
        this.dispatcher = dispatcher;

        setContentPane(mainPanel);
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        setSize(CONVERSATION_FRAME_WIDTH, CONVERSATION_FRAME_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        sendButton.addActionListener(e -> onSendMessage());
        messageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    onSendMessage();
            }
        });

        getConversation();
        setVisible(true);
    }

    private void getConversation() {
        //dispatcher.dispatch();
        //conversationTextArea.append()
    }

    private void onSendMessage() {
        Date date = new Date();
        String content = messageTextField.getText();

        dispatcher.dispatch(new SendMessageEvent(content, date, userLogin, users));

        messageTextField.setText("");
        conversationTextArea.append(simpleDateFormat.format(date) + " " + userLogin + " > " + content + "\n");
    }

    public List<String> getUsers() {
        return users;
    }

    public void displayMessage(String content, Date date, String sender) {
        conversationTextArea.append(simpleDateFormat.format(date) + " " + sender + " > " + content + "\n");
    }

    public void displayConversation() {
        //for each message
    }
}
