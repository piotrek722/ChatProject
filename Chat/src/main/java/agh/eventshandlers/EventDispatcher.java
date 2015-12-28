package agh.eventshandlers;

import agh.router.Channel;
import agh.router.DynamicRouter;
import agh.router.Message;

import java.util.HashMap;
import java.util.Map;

public class EventDispatcher implements DynamicRouter {
    private Map<Class<? extends Message>, Channel> handlers;

    public EventDispatcher() {
        this.handlers = new HashMap<>();
    }

    @Override
    public void registerChannel(Class contentType, Channel channel) {
        handlers.put(contentType, channel);
    }

    @Override
    public void dispatch(Message content) {
        handlers.get(content.getType()).dispatch(content);
    }
}
