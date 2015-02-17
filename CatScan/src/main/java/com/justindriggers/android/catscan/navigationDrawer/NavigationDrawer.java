package com.justindriggers.android.catscan.navigationDrawer;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.justindriggers.android.catscan.R;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawer {

    private Activity mActivity;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mDrawerContainer;
    private NavigationDrawerAdapter mAdapter;

    public NavigationDrawer(Activity parentActivity) {
        this.mActivity = parentActivity;

        initDrawerLayout();
        initDrawerList();
    }

    private void initDrawerLayout() {
        this.mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer);
        this.mDrawerContainer = mActivity.findViewById(R.id.drawerContainer);

        mActionBarDrawerToggle = new MainActionBarDrawerToggle(mActivity, mDrawerLayout, (Toolbar) mActivity.findViewById(R.id.toolbar));

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    private void initDrawerList() {
        RecyclerView recyclerView = (RecyclerView) mDrawerContainer.findViewById(R.id.drawerRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        mAdapter = new NavigationDrawerAdapter(mActivity.getApplicationContext(), getMenu());
        recyclerView.setAdapter(mAdapter);
    }

    public void setSelectedIndex(int position) {
        mAdapter.setSelectedIndex(position);
    }

    public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        mAdapter.setOnNavigationSelectedListener(onNavigationItemSelectedListener);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mDrawerContainer);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mDrawerContainer);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mDrawerContainer);
    }

    private List<NavigationItem> getMenu() {
        List<NavigationItem> items = new ArrayList<>();
        items.add(new NavigationItem("Main", mActivity.getResources().getDrawable(R.drawable.ic_smartphone_black_24dp)));
        items.add(new NavigationItem("Event", mActivity.getResources().getDrawable(R.drawable.ic_info_black_24dp)));
        items.add(new NavigationItem("Modem", mActivity.getResources().getDrawable(R.drawable.ic_network_cell_black_24dp)));
        items.add(new NavigationItem("Audit", mActivity.getResources().getDrawable(R.drawable.ic_https_black_24dp)));
        items.add(new NavigationItem("Kernel", mActivity.getResources().getDrawable(R.drawable.ic_bug_report_black_24dp)));
        items.add(new NavigationItem("Last Kernel", mActivity.getResources().getDrawable(R.drawable.ic_history_black_24dp)));
        return items;
    }
}
