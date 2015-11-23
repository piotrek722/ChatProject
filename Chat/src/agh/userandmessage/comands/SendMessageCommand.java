package agh.userandmessage.comands;

import agh.userandmessage.model.Message;

/**
 * Created by Asia on 2015-11-23.
 */
public class SendMessageCommand implements Command {
    public Message message;

    public SendMessageCommand(Message message) {
        this.message=message;
    }

    @Override
    public void execute() {
        //sending message to receivers and writing to database
    }

}
