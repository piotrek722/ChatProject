package agh.router;

public interface DynamicRouter <E extends Message> {
    void registerChannel(Class<? extends E> contentType, Channel<? extends E> channel);
    void dispatch(E content);
}
