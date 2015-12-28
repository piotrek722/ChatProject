package agh.eventshandlers;

import agh.router.Message;

public class SendButtonHandler extends GUIHandler {
    private String message; //Class Message from Model package

    public SendButtonHandler(String message) {
        super();
        this.message = message;
    }

    @Override
    public void dispatch(Message message) {

    }
}
