package agh.userandmessage.comands;

import agh.userandmessage.model.Contact;
import agh.userandmessage.model.User;

/**
 * Created by Asia on 2015-11-23.
 */
public class DeleteContactCommand implements Command {

    public User user;
    public Contact contact;

    public DeleteContactCommand(User user, Contact contact) {
        this.user=user;
        this.contact=contact;
    }

    @Override
    public void execute() {
        //deleting contact from user's contact list
    }

}
