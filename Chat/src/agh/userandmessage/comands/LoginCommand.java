package agh.userandmessage.comands;

import agh.userandmessage.model.User;

/**
 * Created by Asia on 2015-11-23.
 */
public class LoginCommand implements Command  {

    public User user;

    public LoginCommand(User user) {
        this.user=user;
    }

    @Override
    public void execute() {
        //logging user, checking if password and login matches
    }

}
