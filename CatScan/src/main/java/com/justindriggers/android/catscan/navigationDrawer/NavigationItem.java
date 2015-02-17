package com.justindriggers.android.catscan.navigationDrawer;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

public class NavigationItem {

    private Fragment mFragment;
    private String mText;
    private Drawable mDrawable;

    public NavigationItem(String text, Drawable drawable, Fragment fragment) {
        mText = text;
        mDrawable = drawable;
        mFragment = fragment;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }
}