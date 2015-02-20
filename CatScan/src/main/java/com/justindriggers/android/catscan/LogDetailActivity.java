package com.justindriggers.android.catscan;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.StyleableRes;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;


public class LogDetailActivity extends ActionBarActivity {

    private LinearLayout mLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);

        mLayout = (LinearLayout) findViewById(R.id.layout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTheme(getIntent().getIntExtra("type", R.style.VerboseTheme));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
