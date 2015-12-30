package agh.handlers;

import agh.clientgui.SearchDialog;
import agh.events.ShowSearchEvent;
import agh.router.Channel;

public class ShowSearchHandler implements Channel<ShowSearchEvent> {
    private SearchDialog searchDialog;

    public ShowSearchHandler(SearchDialog searchDialog) {
        this.searchDialog = searchDialog;
    }

    @Override
    public void dispatch(ShowSearchEvent message) {
        searchDialog.setVisible(true);
    }
}
