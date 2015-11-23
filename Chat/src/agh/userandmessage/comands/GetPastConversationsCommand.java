package agh.userandmessage.comands;

import agh.userandmessage.model.ContactList;
import agh.userandmessage.model.Conversation;
import agh.userandmessage.model.User;

/**
 * Created by Asia on 2015-11-23.
 */
public class GetPastConversationsCommand implements Command {

    public User user;
    public ContactList contacts;
    public Conversation conversation;

    public GetPastConversationsCommand(User user, ContactList contacts, Conversation conversation) {
        this.user=user;
        this.contacts= new ContactList(contacts);
        this.conversation= new Conversation(conversation);
    }

    @Override
    public void execute() {
        //
    }

}
