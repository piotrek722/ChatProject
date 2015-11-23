package agh.userandmessage.comands;

import agh.userandmessage.model.ContactList;

/**
 * Created by Asia on 2015-11-23.
 */
public class GetContactsCommand implements  Command {

    public ContactList contacts;

    public GetContactsCommand(ContactList contacts) {
        this.contacts= new ContactList(contacts);
    }

    @Override
    public void execute() {
        //returning user's contact list
    }

}
