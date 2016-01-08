package agh.client.gui.mainframe.events;

import agh.router.DefaultEvent;

public class GetContactsEvent extends DefaultEvent {
    private String userLogin;

    public GetContactsEvent(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return userLogin;
    }


}
