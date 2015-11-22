package agh;

import agh.userandmessage.database.controllers.ContactCsvController;
import agh.userandmessage.database.controllers.ContactListCsvController;
import agh.userandmessage.database.controllers.UserCsvController;
import agh.userandmessage.model.Contact;
import agh.userandmessage.model.User;

/**
 * Created by Peter on 2015-11-21.
 * Project name : ChatProject
 */
public class ServerMain {

    public static void main(String[] args) {

        User user = new User("user","haslo","Ktos","Jakis");

        UserCsvController.saveUser(user);
        System.out.println(UserCsvController.readUser(user.getLogin()));

        Contact contact = new Contact(user);
        ContactCsvController.saveContact(contact);
        System.out.println(ContactCsvController.readContact(contact.getLogin()));

        User user1 = new User("user1","haslo","Ktos","Jakis");
        UserCsvController.saveUser(user1);
        System.out.println(UserCsvController.readUser(user1.getLogin()));

        Contact contact1 = new Contact(user1);
        ContactCsvController.saveContact(contact1);
        System.out.println(ContactCsvController.readContact(contact1.getLogin()));

        user.getContactList().add(contact,contact1);
        ContactListCsvController.saveContactList(user);
        System.out.println("Contact list for user : ");
        System.out.println(ContactListCsvController.findforUser(user.getLogin()));


    }
}
