package agh;

import agh.userandmessage.comands.AddContactCommand;
import agh.userandmessage.comands.AddUserCommand;
import agh.userandmessage.comands.Command;
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

        //Commands from Client to Server

        AddUserCommand addUserCommand = new AddUserCommand(user);
        receiveAsObject(addUserCommand);
        addUserCommand = new AddUserCommand(user1);
        receiveAsObject(addUserCommand);

        AddContactCommand addContactCommand = new AddContactCommand(user,contact);
        receiveAsObject(addContactCommand);
        addContactCommand = new AddContactCommand(user,contact1);
        receiveAsObject(addContactCommand);

    }

    public static void receiveAsObject(Object o){
        //we will receive it from ObjectStreamInput as Object
        Command c = (Command) o;
        c.execute();
    }

}