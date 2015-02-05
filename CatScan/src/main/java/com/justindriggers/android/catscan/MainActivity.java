package com.justindriggers.android.catscan;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StyleableRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.justindriggers.android.catscan.navigationDrawer.NavigationDrawer;
import com.justindriggers.android.catscan.navigationDrawer.NavigationDrawerCallbacks;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final List<Fragment> FRAGMENTS = new ArrayList<>();

    static {
        LoggingFragment loggingFragment = new LoggingFragment();

        FRAGMENTS.add(loggingFragment);
        FRAGMENTS.add(loggingFragment);
        FRAGMENTS.add(loggingFragment);
    }

    private Toolbar mToolbar;
    private NavigationDrawer mNavigationDrawer;
    private int mSelectedNavPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawer = new NavigationDrawer(this);

        if(savedInstanceState != null) {
            mSelectedNavPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }

//        mNavigationDrawer.setSelectedItem(mSelectedNavPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mSelectedNavPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mNavigationDrawer.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mNavigationDrawer.isDrawerOpen()) {
            mNavigationDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setTheme(@StyleableRes int resid) {
        super.setTheme(resid);

        TypedArray attributes = obtainStyledAttributes(resid, new int[]{R.attr.colorPrimary, R.attr.colorPrimaryDark});
        int toolBarColor = attributes.getColor(0, R.color.verbose);
        int statusBarColor = attributes.getColor(1, R.color.verbose_dark);

        if(mToolbar != null) {
            mToolbar.setBackgroundColor(toolBarColor);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(statusBarColor);
//            getWindow().setNavigationBarColor(statusBarColor);
        }
    }

    /**
     * mAdapter.getItem(position).doSomething();
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, FRAGMENTS.get(position)).commit();
    }
}
