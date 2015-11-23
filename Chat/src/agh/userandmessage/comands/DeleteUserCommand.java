package agh.userandmessage.comands;

import agh.userandmessage.model.User;

/**
 * Created by Asia on 2015-11-23.
 */
public class DeleteUserCommand implements Command {

    public User user;

    public DeleteUserCommand(User user) {
        this.user=user;
    }

    @Override
    public void execute() {
        //deleting user account
    }
}
