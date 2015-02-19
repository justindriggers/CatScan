package com.justindriggers.android.catscan;

import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class LoggingFragment extends Fragment implements OnLogEntityClickListener, OnScrollToBottomListener {

    private boolean mVisible = false;
    private boolean mAutoScroll = true;

    private List<LogEntity> mLogs;
    private LogEntityAdapter mAdapter;
    private Handler mHandler;
    private LogReaderTask mLogReaderTask;
    private ImageView mActionButton;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogs = new ArrayList<>();
        mAdapter = new LogEntityAdapter(getActivity(), mLogs);
        mAdapter.setOnLogEntityClickListener(this);
        mAdapter.setOnDataSetChangedListener(new OnDataSetChangedListener() {
            @Override
            public void onItemInserted(int position) {
                if (mAutoScroll) {
                    mRecyclerView.scrollToPosition(position);
                    toggleButton(false);
                }
            }
        });

        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mAdapter);
        }

        mHandler = new LogHandler(mAdapter);

        mLogReaderTask = new LogReaderTask(mHandler);
        mLogReaderTask.execute();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logging, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.loggingRecyclerView);
        mRecyclerView.addItemDecoration(new SimpleRecyclerViewDivider(getActivity()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setOnScrollListener(new LogScrollListener(layoutManager, this));

        if (mAdapter != null) {
            mRecyclerView.setAdapter(mAdapter);
        }

        mActionButton = (ImageButton) v.findViewById(R.id.floatingActionButton);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                toggleButton(false);
                mAutoScroll = true;
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
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void onDestroy() {
        mLogReaderTask.stopTask();
        super.onDestroy();
    }

    @Override
    public void onLogEntityClick(View source, LogEntity logEntity) {
        getActivity().setTheme(logEntity.getPriority().getThemeResource());
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
        mAutoScroll = isAtBottom;
    }
}
