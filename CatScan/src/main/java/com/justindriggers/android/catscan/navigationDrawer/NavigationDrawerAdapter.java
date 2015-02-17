package com.justindriggers.android.catscan.navigationDrawer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.justindriggers.android.catscan.R;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationViewHolder> {

    private static final float DEFAULT_TEXT_ALPHA = 0.87f;
    private static final float DEFAULT_ICON_ALPHA = 0.54f;
    private static final float SELECTED_ALPHA = 1.0f;

    private List<NavigationItem> mNavigationItems;
    private OnNavigationItemSelectedListener mOnNavigationSelectedListener;
    private int mSelectedColor = Color.BLACK;

    private int mSelectedIndex;

    public NavigationDrawerAdapter(Context context, List<NavigationItem> items) {
        mSelectedColor = context.getResources().getColor(R.color.default_primary);

        if (items == null) {
            this.mNavigationItems = new ArrayList<>();
        } else {
            this.mNavigationItems = items;
        }
    }

    @Override
    public NavigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_row, parent, false);

        return new NavigationViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mNavigationItems.size();
    }

    @Override
    public void onBindViewHolder(NavigationViewHolder holder, final int position) {
        NavigationItem item = mNavigationItems.get(position);

        if(item != null) {
            TextView textView = holder.textView;
            textView.setCompoundDrawablesWithIntrinsicBounds(item.getDrawable(), null, null, null);
            textView.setText(item.getText());

            Drawable icon = textView.getCompoundDrawables()[0].mutate();

            if(position == mSelectedIndex) {
                textView.setTextColor(mSelectedColor);
                textView.setAlpha(SELECTED_ALPHA);
                icon.setAlpha((int) (255 * SELECTED_ALPHA));
                icon.setColorFilter(mSelectedColor, PorterDuff.Mode.SRC_ATOP);
            } else {
                textView.setTextColor(Color.BLACK);
                textView.setAlpha(DEFAULT_TEXT_ALPHA);
                icon.setAlpha((int) (255 * DEFAULT_ICON_ALPHA));
                icon.clearColorFilter();
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedIndex(position);
            }
        });
    }

    public void setOnNavigationSelectedListener(OnNavigationItemSelectedListener onNavigationSelectedListener) {
        this.mOnNavigationSelectedListener = onNavigationSelectedListener;
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    public void setSelectedIndex(int position) {
        int oldPosition = mSelectedIndex;
        this.mSelectedIndex = position;

        notifyItemChanged(oldPosition);
        notifyItemChanged(position);

        if (mOnNavigationSelectedListener != null) {
            mOnNavigationSelectedListener.onNavigationItemSelected(mNavigationItems.get(position));
        }
    }
}