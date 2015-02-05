package com.justindriggers.android.catscan.navigationDrawer;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.justindriggers.android.catscan.R;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawer implements AdapterView.OnItemClickListener {

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
        ListView drawerList = (ListView) mDrawerContainer.findViewById(R.id.drawerList);

        final List<NavigationItem> navigationItems = getMenu();
        mAdapter = new NavigationDrawerAdapter(mActivity.getApplicationContext(), navigationItems);
//        mAdapter.setNavigationDrawerCallbacks(mActivity);
        drawerList.setAdapter(mAdapter);
        drawerList.setOnItemClickListener(this);
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

    public List<NavigationItem> getMenu() {
        List<NavigationItem> items = new ArrayList<>();
        items.add(new NavigationItem("Main", mActivity.getResources().getDrawable(R.drawable.ic_smartphone_black_24dp)));
        items.add(new NavigationItem("Event", mActivity.getResources().getDrawable(R.drawable.ic_info_black_24dp)));
        items.add(new NavigationItem("Modem", mActivity.getResources().getDrawable(R.drawable.ic_network_cell_black_24dp)));
        items.add(new NavigationItem("Audit", mActivity.getResources().getDrawable(R.drawable.ic_https_black_24dp)));
        items.add(new NavigationItem("Kernel", mActivity.getResources().getDrawable(R.drawable.ic_bug_report_black_24dp)));
        items.add(new NavigationItem("Last Kernel", mActivity.getResources().getDrawable(R.drawable.ic_history_black_24dp)));
        return items;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if(position != mAdapter.getSelectedIndex()) {
            // TODO Activity stuff
            closeDrawer();
            mAdapter.setSelectedIndex(position);
            mAdapter.notifyDataSetChanged();
        }
    }
}
