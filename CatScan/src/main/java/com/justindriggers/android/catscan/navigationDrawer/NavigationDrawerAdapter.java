package com.justindriggers.android.catscan.navigationDrawer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.justindriggers.android.catscan.R;

import java.util.List;

public class NavigationDrawerAdapter extends ArrayAdapter<NavigationItem> {

    private static final float DEFAULT_TEXT_ALPHA = 0.87f;
    private static final float DEFAULT_ICON_ALPHA = 0.54f;
    private static final float SELECTED_ALPHA = 1.0f;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<NavigationItem> mNavigationItems;
    private int mSelectedIndex = 0;
    private int selectedColor;

    public NavigationDrawerAdapter(Context context, List<NavigationItem> navigationItems) {
        super(context, R.layout.drawer_row, navigationItems);
        this.mContext = context;
        this.mNavigationItems = navigationItems;

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        selectedColor = mContext.getResources().getColor(R.color.default_primary);
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

            Drawable icon = textView.getCompoundDrawables()[0].mutate();

            if(position == mSelectedIndex) {
                textView.setTextColor(selectedColor);
                textView.setAlpha(SELECTED_ALPHA);
                icon.setAlpha((int) (255 * SELECTED_ALPHA));
                icon.setColorFilter(selectedColor, PorterDuff.Mode.SRC_ATOP);
            } else {
                textView.setTextColor(Color.BLACK);
                textView.setAlpha(DEFAULT_TEXT_ALPHA);
                icon.setAlpha((int) (255 * DEFAULT_ICON_ALPHA));
                icon.clearColorFilter();
            }
        }

        return view;
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    public void setSelectedIndex(int mSelectedIndex) {
        this.mSelectedIndex = mSelectedIndex;
    }
}