package agh;

import agh.userandmessage.comands.AddContactCommand;
import agh.userandmessage.comands.AddUserCommand;
import agh.userandmessage.model.Contact;
import agh.userandmessage.model.User;

/**
 * Created by Peter on 2015-11-21.
 * Project name : ChatProject
 */
public class ServerMain {

    public static void main(String[] args) {

        User user = new User("user","haslo","Ktos","Jakis");
        User user1 = new User("user1","haslo","Ktos","Jakis");
        Contact contact = new Contact(user);
        Contact contact1 = new Contact(user1);
        user.getContactList().add(contact,contact1);

        //Commands

        AddUserCommand addUserCommand = new AddUserCommand(user);
        addUserCommand.execute();
        addUserCommand = new AddUserCommand(user1);
        addUserCommand.execute();

        AddContactCommand addContactCommand = new AddContactCommand(user,contact);
        addContactCommand.execute();
        addContactCommand = new AddContactCommand(user,contact1);
        addContactCommand.execute();






    }
}