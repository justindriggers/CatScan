package com.justindriggers.android.catscan.navigationDrawer;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.justindriggers.android.catscan.MainActivity;
import com.justindriggers.android.catscan.R;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawer {

    private MainActivity mActivity;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;
    private NavigationDrawerAdapter mAdapter;

    public NavigationDrawer(MainActivity activity, Toolbar toolbar) {
        this.mActivity = activity;

        mFragmentContainerView = mActivity.findViewById(R.id.drawerContainer);

        initDrawerLayout(toolbar);
        initDrawerList();
    }

    private void initDrawerLayout(Toolbar toolbar) {
        mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer);

        mActionBarDrawerToggle = new MainActionBarDrawerToggle(mActivity, mDrawerLayout, toolbar);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    private void initDrawerList() {
        RecyclerView drawerList = (RecyclerView) mActivity.findViewById(R.id.drawerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        drawerList.setLayoutManager(layoutManager);
        drawerList.setHasFixedSize(true);

        final List<NavigationItem> navigationItems = getMenu();
        mAdapter = new NavigationDrawerAdapter(navigationItems);
        mAdapter.setNavigationDrawerCallbacks(mActivity);
        drawerList.setAdapter(mAdapter);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    public List<NavigationItem> getMenu() {
        List<NavigationItem> items = new ArrayList<>();
        items.add(new NavigationItem("item 1", mActivity.getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new NavigationItem("item 2", mActivity.getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new NavigationItem("item 3", mActivity.getResources().getDrawable(R.drawable.ic_launcher)));
        return items;
    }

    public void setSelectedItem(int position) {
        mAdapter.selectPosition(position);
    }
}
