package com.justindriggers.android.catscan.navigationDrawer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.justindriggers.android.catscan.R;

public class NavigationViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public NavigationViewHolder(View itemView) {
        super(itemView);
        this.textView = (TextView) itemView.findViewById(R.id.item_name);
    }

}
