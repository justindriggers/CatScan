package com.justindriggers.android.catscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LogEntityAdapter extends ArrayAdapter<LogEntity> {

    private LayoutInflater mLayoutInflater;
    private List<LogEntity> logEntities = null;

    public LogEntityAdapter(Context context, List<LogEntity> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.logEntities = objects;

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return logEntities != null ? logEntities.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public LogEntity getItem(int position) {
        return logEntities != null ? logEntities.get(position) : null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        LogEntity data = getItem(position);

        if (data != null) {
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(data.getMessage());
            textView.setTextColor(getContext().getResources().getColor(data.getPriority().getColorResource()));
        }

        return view;
    }

}
