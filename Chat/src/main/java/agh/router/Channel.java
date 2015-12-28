package agh.router;

public interface Channel <E extends Message> {
    void dispatch(E message);
}
