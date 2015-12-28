package agh.router;

public interface Message {
    Class<? extends Message> getType();
}
