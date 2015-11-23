package agh.userandmessage.comands;

import agh.userandmessage.database.controllers.ContactCsvController;
import agh.userandmessage.database.controllers.UserCsvController;
import agh.userandmessage.model.Contact;
import agh.userandmessage.model.User;

/**
 * Created by Peter on 2015-11-22.
 * Project name : ChatProject
 */
public class AddUserCommand implements Command {

    public User userToAdd;

    public AddUserCommand(User userToAdd) {
        this.userToAdd = userToAdd;
    }

    @Override
    public void execute() {
        UserCsvController.saveUser(userToAdd);
        ContactCsvController.saveContact(new Contact(userToAdd));
    }
}
