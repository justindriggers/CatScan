package com.justindriggers.android.catscan;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.StyleableRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import com.justindriggers.android.catscan.navigationDrawer.NavigationDrawer;
import com.justindriggers.android.catscan.navigationDrawer.OnNavigationItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements OnNavigationItemSelectedListener {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final List<Fragment> FRAGMENTS = new ArrayList<>();

    static {
        LoggingFragment loggingFragment = new LoggingFragment();

        FRAGMENTS.add(loggingFragment);
        FRAGMENTS.add(loggingFragment);
        FRAGMENTS.add(loggingFragment);
    }

    private LinearLayout mLayout;
    private Toolbar mToolbar;
    private NavigationDrawer mNavigationDrawer;
    private int mSelectedNavPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = (LinearLayout) findViewById(R.id.layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawer = new NavigationDrawer(this);
        mNavigationDrawer.setOnNavigationItemSelectedListener(this);

        if(savedInstanceState != null) {
            mSelectedNavPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }

        mNavigationDrawer.setSelectedIndex(mSelectedNavPosition);
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

        if (mLayout != null) {
            mLayout.setBackgroundColor(toolBarColor);
        }

        if (mToolbar != null) {
            mToolbar.setBackgroundColor(toolBarColor);
        }
    }

    /**
     * doSomethingWith(mAdapter.getItem(position));
     */
    @Override
    public void onNavigationItemSelected(int position) {
        mNavigationDrawer.closeDrawer();

        if (position < FRAGMENTS.size()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, FRAGMENTS.get(position)).commit();
        }
    }
}
