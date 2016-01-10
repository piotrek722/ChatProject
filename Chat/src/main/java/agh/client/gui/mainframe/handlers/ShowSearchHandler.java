package agh.client.gui.mainframe.handlers;

import agh.client.gui.searchdialog.SearchDialog;
import agh.client.gui.mainframe.events.ShowSearchEvent;
import agh.router.Handler;

public class ShowSearchHandler implements Handler<ShowSearchEvent> {
    private SearchDialog searchDialog;

    public ShowSearchHandler(SearchDialog searchDialog) {
        this.searchDialog = searchDialog;
    }

    @Override
    public void dispatch(ShowSearchEvent message) {
        searchDialog.setUser(message.getUser());
        searchDialog.setVisible(true);
    }
}
