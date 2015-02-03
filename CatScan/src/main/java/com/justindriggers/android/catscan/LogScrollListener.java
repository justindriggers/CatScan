package com.justindriggers.android.catscan;

import android.widget.AbsListView;
import android.widget.ListView;

public class LogScrollListener implements AbsListView.OnScrollListener {

    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE && firstVisibleItem + visibleItemCount >= totalItemCount) {
            view.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        } else {
            view.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
    }
}
