package agh.client.events;

import agh.router.DefaultEvent;

public class SendMessageEvent extends DefaultEvent {
    private String message;

    public SendMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
