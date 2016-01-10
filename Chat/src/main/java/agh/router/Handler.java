package agh.router;

public interface Handler<M extends Event> {
    void dispatch(M message);
}
