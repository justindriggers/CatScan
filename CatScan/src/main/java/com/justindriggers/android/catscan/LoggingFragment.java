package com.justindriggers.android.catscan;

import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LoggingFragment extends ListFragment implements OnScrollToBottomListener {

    private boolean mVisible = false;

    private List<LogEntity> mLogs;
    private LogEntityAdapter mAdapter;
    private Handler mHandler;
    private LogReaderTask mLogReaderTask;
    private ImageView mActionButton;

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
        View v = inflater.inflate(R.layout.fragment_logging, container, false);

        final ListView listView = (ListView) v.findViewById(android.R.id.list);
        listView.setOnScrollListener(new LogScrollListener(this));

        mActionButton = (ImageButton) v.findViewById(R.id.floatingActionButton);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setSelection(listView.getCount() - 1);
                toggleButton(false);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        int size = getResources().getDimensionPixelSize(R.dimen.action_button_size);
                        outline.setOval(0, 0, size, size);
                    }
                }
            };
            mActionButton.setOutlineProvider(viewOutlineProvider);
        }

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
        getActivity().setTheme(mAdapter.getItem(position).getPriority().getThemeResource());
    }

    private void toggleButton(final boolean visible) {
        if (mVisible != visible) {
            mVisible = visible;

            int marginBottom = 0;
            final ViewGroup.LayoutParams layoutParams = mActionButton.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            }

            int translationY = visible ? 0 : mActionButton.getHeight() + marginBottom;

            ViewCompat.animate(mActionButton).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(200).translationY(translationY);
        }
    }

    @Override
    public void onScroll(boolean isAtBottom) {
        toggleButton(!isAtBottom);
    }
}
