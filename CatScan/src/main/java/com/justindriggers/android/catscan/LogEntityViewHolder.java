package com.justindriggers.android.catscan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class LogEntityViewHolder extends RecyclerView.ViewHolder {

    View mLevelIndicator;
    TextView mTextView;

    private OnLogEntityClickListener mOnLogEntityClickListener;
    private LogEntity mLogEntity;

    public LogEntityViewHolder(View itemView, OnLogEntityClickListener onLogEntityClickListener) {
        super(itemView);
        this.mLevelIndicator = itemView.findViewById(R.id.levelIndicator);
        this.mTextView = (TextView) itemView.findViewById(R.id.text);
        this.mOnLogEntityClickListener = onLogEntityClickListener;

        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnLogEntityClickListener != null && mLogEntity != null) {
                    mOnLogEntityClickListener.onLogEntityClick(LogEntityViewHolder.this, mLogEntity);
                }
            }
        });
    }

    public void bind(LogEntity logEntity) {
        this.mLogEntity = logEntity;
    }
}
