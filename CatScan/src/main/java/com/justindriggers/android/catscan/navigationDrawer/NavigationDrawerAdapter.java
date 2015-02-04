package com.justindriggers.android.catscan.navigationDrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.justindriggers.android.catscan.R;

import java.util.List;

public class NavigationDrawerAdapter extends ArrayAdapter<NavigationItem> {

    private LayoutInflater mLayoutInflater;
    private List<NavigationItem> mNavigationItems;

    public NavigationDrawerAdapter(Context context, List<NavigationItem> navigationItems) {
        super(context, R.layout.drawer_row, navigationItems);
        mNavigationItems = navigationItems;

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mNavigationItems != null ? mNavigationItems.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public NavigationItem getItem(int position) {
        return mNavigationItems != null ? mNavigationItems.get(position) : null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            view = mLayoutInflater.inflate(R.layout.drawer_row, null);
        }

        NavigationItem item = getItem(position);

        if(item != null) {
            TextView textView = (TextView) view.findViewById(R.id.item_name);
            textView.setCompoundDrawablesWithIntrinsicBounds(item.getDrawable(), null, null, null);
            textView.setText(item.getText());
        }

        return view;
    }

}