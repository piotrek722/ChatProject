package agh.client.handlers;

import agh.client.gui.SearchDialog;
import agh.client.events.ShowSearchEvent;
import agh.router.Handler;

public class ShowSearchHandler implements Handler<ShowSearchEvent> {
    private SearchDialog searchDialog;

    public ShowSearchHandler(SearchDialog searchDialog) {
        this.searchDialog = searchDialog;
    }

    @Override
    public void dispatch(ShowSearchEvent message) {
        searchDialog.setVisible(true);
    }
}
