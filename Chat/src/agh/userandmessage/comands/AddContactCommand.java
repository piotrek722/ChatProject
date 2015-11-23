package agh.userandmessage.comands;

import agh.userandmessage.database.controllers.ContactListCsvController;
import agh.userandmessage.model.Contact;
import agh.userandmessage.model.User;

/**
 * Created by Asia on 2015-11-23.
 */
public class AddContactCommand implements  Command{

    public User user;
    public Contact contact;

    public AddContactCommand(User user, Contact contact) {
        this.user = user;
        this.contact = contact;
    }

    @Override
    public void execute() {
        user.getContactList().add(contact);
        ContactListCsvController.saveContactList(user);
    }

}