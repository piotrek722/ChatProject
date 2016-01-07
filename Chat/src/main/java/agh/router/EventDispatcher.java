package agh.router;

public interface EventDispatcher<E extends Event> {
    void registerChannel(Class<? extends E> contentType, Handler<? extends E> handler);
    void dispatch(E content);
}
