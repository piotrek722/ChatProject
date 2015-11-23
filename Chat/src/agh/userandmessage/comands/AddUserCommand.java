package agh.userandmessage.comands;

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
        //adding user to database
    }
}
