package agh.router;

public class DefaultEvent implements Event {
    @Override
    public Class<? extends Event> getType() {
        return getClass();
    }
}
