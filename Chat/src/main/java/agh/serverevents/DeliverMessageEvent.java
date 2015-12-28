package agh.serverevents;

import agh.client.IClient;
import agh.router.Event;

public class DeliverMessageEvent extends Event {
    private IClient client;
    private String message;

    public DeliverMessageEvent(IClient client, String message) {
        this.client = client;
        this.message = message;
    }

    public IClient getClient() {
        return client;
    }

    public String getMessage() {
        return message;
    }
}
