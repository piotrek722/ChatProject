package agh.router;

import java.util.HashMap;
import java.util.Map;

public class DefaultEventDispatcher implements EventDispatcher {
    private Map<Class<? extends Event>, Handler> handlers;

    public DefaultEventDispatcher() {
        this.handlers = new HashMap<>();
    }

    @Override
    public void registerChannel(Class contentType, Handler handler) {
        handlers.put(contentType, handler);
    }

    @Override
    public void dispatch(Event content) {
        handlers.get(content.getType()).dispatch(content);
    }
}
