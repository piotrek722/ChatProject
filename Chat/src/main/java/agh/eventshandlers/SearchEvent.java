package agh.eventshandlers;

import agh.router.Event;

public class SearchEvent extends Event {
    private String nick;
    private String fname;
    private String lname;

    public SearchEvent(String nick, String fname, String lname) {
        this.nick = nick;
        this.fname = fname;
        this.lname = lname;
    }

    public String getNick() {
        return nick;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }
}
