package com.justindriggers.android.catscan;

import android.support.v7.widget.RecyclerView;

public class LogScrollListener extends RecyclerView.OnScrollListener {

    private RecyclerView.LayoutManager mLayoutManager;
    private OnScrollToBottomListener mOnScrollToBottomListener;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;

    public LogScrollListener(RecyclerView.LayoutManager layoutManager, OnScrollToBottomListener onScrollToBottomListener) {
        this.mLayoutManager = layoutManager;
        this.mOnScrollToBottomListener = onScrollToBottomListener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView view, int scrollState) {
        if (scrollState == RecyclerView.SCROLL_STATE_IDLE && firstVisibleItem + visibleItemCount >= totalItemCount) {
//            view.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

            if (mOnScrollToBottomListener != null) {
                mOnScrollToBottomListener.onScroll(true);
            }
        } else {
//            view.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);

            if (mOnScrollToBottomListener != null) {
                mOnScrollToBottomListener.onScroll(false);
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        this.firstVisibleItem = mLayoutManager.getPosition(mLayoutManager.getChildAt(0));
        this.visibleItemCount = mLayoutManager.getChildCount();
        this.totalItemCount = mLayoutManager.getItemCount();
    }
}
