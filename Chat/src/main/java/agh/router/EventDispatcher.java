package agh.router;

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
