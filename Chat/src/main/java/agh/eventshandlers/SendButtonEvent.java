package agh.eventshandlers;

import agh.router.Message;

public class SendButtonEvent implements Message {
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
