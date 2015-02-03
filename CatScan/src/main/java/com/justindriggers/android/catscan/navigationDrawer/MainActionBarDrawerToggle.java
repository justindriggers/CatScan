package com.justindriggers.android.catscan.navigationDrawer;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.justindriggers.android.catscan.R;

public class MainActionBarDrawerToggle extends ActionBarDrawerToggle {

    private Activity mActivity;

    public MainActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar) {
        super(activity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        this.mActivity = activity;
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        mActivity.invalidateOptionsMenu();
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        mActivity.invalidateOptionsMenu();
    }
}
