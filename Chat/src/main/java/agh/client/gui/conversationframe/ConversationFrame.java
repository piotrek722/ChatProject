package agh.client.gui.conversationframe;

import agh.client.gui.conversationframe.events.GetConversationEvent;
import agh.client.gui.conversationframe.events.SendMessageEvent;
import agh.model.simple.ClientMessage;
import agh.model.simple.Conversation;
import agh.model.simple.SimplifiedUser;
import agh.router.EventDispatcher;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ConversationFrame extends JFrame{
    private JTextArea conversationTextArea;
    private JPanel mainPanel;
    private JTextField messageTextField;
    private JButton sendButton;

    private SimplifiedUser user;
    private List<SimplifiedUser> participants;
    private EventDispatcher dispatcher;

    private static final int CONVERSATION_FRAME_WIDTH = 400;
    private static final int CONVERSATION_FRAME_HEIGHT = 300;

    public ConversationFrame(SimplifiedUser user, List<SimplifiedUser> participants, EventDispatcher dispatcher) throws UnsupportedLookAndFeelException {
        super(String.join(", ", participants.stream().map(e -> e.getLogin()).collect(Collectors.toList())));
        this.user = user;
        this.participants = participants;
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
        List<String> list = participants.stream().map(e -> e.getLogin()).collect(Collectors.toList());
        dispatcher.dispatch(new GetConversationEvent(user.getLogin(), list, this));
    }

    private void onSendMessage() {
        Date date = new Date();
        String content = messageTextField.getText();

        ClientMessage message = new ClientMessage(content, date, user, participants);

        dispatcher.dispatch(new SendMessageEvent(message));

        messageTextField.setText("");
        conversationTextArea.append(message.toString() + "\n");
    }

    public List<SimplifiedUser> getParticipants() {
        return participants;
    }

    public void displayMessage(ClientMessage message) {
        conversationTextArea.append(message.toString() + "\n");
    }

    public void displayConversation(Conversation conversation) {
        for (ClientMessage message : conversation.getMessages()) {
            displayMessage(message);
        }
    }
}
