package com.justindriggers.android.catscan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class LogEntityAdapter extends RecyclerView.Adapter<LogEntityViewHolder> {

    private Context mContext;
    private OnLogEntityClickListener mOnLogEntityClickListener;
    private OnDataSetChangedListener mOnDataSetChangedListener;
    private List<LogEntity> mLogEntities;

    public LogEntityAdapter(Context context) {
        this.mContext = context;
        this.mLogEntities = new ArrayList<>();
    }

    @Override
    public LogEntityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.logging_row, parent, false);

        return new LogEntityViewHolder(itemView, mOnLogEntityClickListener);
    }

    @Override
    public void onBindViewHolder(LogEntityViewHolder holder, int position) {
        LogEntity item = mLogEntities.get(position);

        if (item != null) {
            holder.bind(item);

            int color = mContext.getResources().getColor(item.getPriority().getColorResource());

            holder.mLevelIndicator.setBackgroundColor(color);
            holder.mTextView.setText(item.getMessage());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mLogEntities != null ? mLogEntities.size() : 0;
    }

    public void add(LogEntity logEntity) {
        mLogEntities.add(logEntity);

        int position = mLogEntities.size() - 1;
        notifyItemInserted(position);

        if (mOnDataSetChangedListener != null) {
            mOnDataSetChangedListener.onItemInserted(position);
        }
    }

    public void addAll(List<LogEntity> logEntities) {
        mLogEntities.addAll(logEntities);
        notifyDataSetChanged();

        if (mOnDataSetChangedListener != null) {
            mOnDataSetChangedListener.onItemInserted(mLogEntities.size() - 1);
        }
    }

    public void setOnLogEntityClickListener(OnLogEntityClickListener onLogEntityClickListener) {
        this.mOnLogEntityClickListener = onLogEntityClickListener;
    }

    public void setOnDataSetChangedListener(OnDataSetChangedListener onDataSetChangedListener) {
        this.mOnDataSetChangedListener = onDataSetChangedListener;
    }

//    @Override
//    public LogEntity getItem(int position) {
//        return mLogEntities != null ? mLogEntities.get(position) : null;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//
//        if (view == null) {
//            view = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, null);
//        }
//
//        LogEntity data = getItem(position);
//
//        if (data != null) {
//            TextView textView = (TextView) view.findViewById(android.R.id.text1);
//            textView.setText(data.getMessage());
//            textView.setTextColor(getContext().getResources().getColor(data.getPriority().getColorResource()));
//        }
//
//        return view;
//    }
}
