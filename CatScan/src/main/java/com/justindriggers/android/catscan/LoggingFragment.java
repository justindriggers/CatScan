package com.justindriggers.android.catscan;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LoggingFragment extends ListFragment {

    private List<LogEntity> mLogs;
    private LogEntityAdapter mAdapter;
    private Handler mHandler;
    private LogReaderTask mLogReaderTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogs = new ArrayList<>();
        mAdapter = new LogEntityAdapter(getActivity(), mLogs);
        setListAdapter(mAdapter);

        mHandler = new LogHandler(mAdapter);

        mLogReaderTask = new LogReaderTask(mHandler);
        mLogReaderTask.execute();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        ListView listView = (ListView) v.findViewById(android.R.id.list);
        listView.setFastScrollEnabled(true);
        listView.setFastScrollAlwaysVisible(true);
        listView.setOnScrollListener(new LogScrollListener());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getListView().setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    @Override
    public void onDestroy() {
        mLogReaderTask.stopTask();
        super.onDestroy();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {

    }
}
