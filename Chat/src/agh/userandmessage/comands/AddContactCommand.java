package agh.userandmessage.comands;

import agh.userandmessage.model.User;

/**
 * Created by Asia on 2015-11-23.
 */
public class AddContactCommand implements  Command{

    public User user;
    public String contact;

    public AddContactCommand(User user, String contact) {
        this.user = user;
        this.contact = contact;
    }

    @Override
    public void execute() {

    }

}