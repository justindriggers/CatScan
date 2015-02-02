package com.justindriggers.android.catscan;

import android.util.Log;

public enum Priority {

    VERBOSE(Log.VERBOSE, "V", R.string.verbose, R.color.verbose),
    DEBUG(Log.DEBUG, "D", R.string.debug, R.color.debug),
    INFO(Log.INFO, "I", R.string.info, R.color.info),
    WARN(Log.WARN, "W", R.string.warn, R.color.warn),
    ERROR(Log.ERROR, "E", R.string.error, R.color.error),
    FATAL(Log.ASSERT, "F", R.string.fatal, R.color.fatal);

    private int value;
    private String abbreviation;
    private int titleResource;
    private int colorResource;

    Priority(int value, String abbreviation, int titleResource, int colorResource) {
        this.value = value;
        this.abbreviation = abbreviation;
        this.titleResource = titleResource;
        this.colorResource = colorResource;
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
}
