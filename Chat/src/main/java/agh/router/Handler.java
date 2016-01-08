package agh.router;

import java.rmi.RemoteException;

public interface Handler<M extends Event> {
    void dispatch(M message) throws RemoteException;
}
