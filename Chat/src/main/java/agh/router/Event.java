package agh.router;

public interface Event {
    Class<? extends Event> getType();
}
