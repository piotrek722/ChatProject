package agh.router;

public interface Channel <M extends Message> {
    void dispatch(M message);
}
