package agh.eventshandlers;

import agh.router.Event;

public class SendMessageEvent extends Event {
    private String message;

    public SendMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
