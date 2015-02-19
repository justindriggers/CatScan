package com.justindriggers.android.catscan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class LogEntityViewHolder extends RecyclerView.ViewHolder {

    TextView mTextView;

    private OnLogEntityClickListener mOnLogEntityClickListener;
    private LogEntity mLogEntity;

    public LogEntityViewHolder(View itemView, final OnLogEntityClickListener onLogEntityClickListener) {
        super(itemView);
        this.mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        this.mOnLogEntityClickListener = onLogEntityClickListener;

        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnLogEntityClickListener != null && mLogEntity != null) {
                    mOnLogEntityClickListener.onLogEntityClick(view, mLogEntity);
                }
            }
        });
    }

    public void setItem(LogEntity logEntity) {
        this.mLogEntity = logEntity;
    }
}
