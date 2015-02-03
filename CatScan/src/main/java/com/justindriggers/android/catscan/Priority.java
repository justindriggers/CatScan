package com.justindriggers.android.catscan;

import android.util.Log;

public enum Priority {

    VERBOSE(Log.VERBOSE, "V", R.string.verbose, R.color.verbose, R.style.VerboseTheme),
    DEBUG(Log.DEBUG, "D", R.string.debug, R.color.debug, R.style.DebugTheme),
    INFO(Log.INFO, "I", R.string.info, R.color.info, R.style.InfoTheme),
    WARN(Log.WARN, "W", R.string.warn, R.color.warn, R.style.WarnTheme),
    ERROR(Log.ERROR, "E", R.string.error, R.color.error, R.style.ErrorTheme),
    FATAL(Log.ASSERT, "F", R.string.fatal, R.color.fatal, R.style.FatalTheme);

    private int value;
    private String abbreviation;
    private int titleResource;
    private int colorResource;
    private int themeResource;

    Priority(int value, String abbreviation, int titleResource, int colorResource, int themeResource) {
        this.value = value;
        this.abbreviation = abbreviation;
        this.titleResource = titleResource;
        this.colorResource = colorResource;
        this.themeResource = themeResource;
    }

    public static Priority getByAbbreviation(String abbreviation) {
        for (Priority priority : Priority.values()) {
            if (priority.getAbbreviation().equals(abbreviation)) {
                return priority;
            }
        }
        return VERBOSE;
    }

    public int getValue() {
        return value;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int getTitleResource() {
        return titleResource;
    }

    public int getColorResource() {
        return colorResource;
    }

    public int getThemeResource() {
        return themeResource;
    }
}
