package agh.client.gui.mainframe.handlers;

import agh.client.gui.mainframe.events.CloseEvent;
import agh.router.Handler;

public class CloseHandler implements Handler<CloseEvent> {

    @Override
    public void dispatch(CloseEvent message) {
        //unregister user from Server
    }
}
