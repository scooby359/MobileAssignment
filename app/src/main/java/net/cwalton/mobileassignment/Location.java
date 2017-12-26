package net.cwalton.mobileassignment;

import android.util.Log;

/**
 * Created by scoob on 24/12/2017.
 * Abstract super class used for City and Country classes.
 * Intended so that homepage can display an array of favourite locations, inclusive of both cities and countries
 */

public class Location {

    public static final String LOC_TYPE_CITY = "city";
    public static final String LOC_TYPE_COUNTRY = "country";
    public static final String LOC_FAV_TRUE = "true";
    public static final String LOC_FAV_FALSE = "false";


    private String mName;
    private String mType;
    private String mWikiUrl;
    private Boolean mFavourite;
    private String mNotes;

    public Location(String mName, String mType, String mWikiUrl) {

        this.mName = mName;
        this.mType = mType;
        this.mWikiUrl = mWikiUrl;

    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmWikiUrl() {
        return mWikiUrl;
    }

    public void setmWikiUrl(String mWikiUrl) {
        this.mWikiUrl = mWikiUrl;
    }

    public Boolean getmFavourite() {
        return mFavourite;
    }

    public void setmFavourite(Boolean mFavourite) {
        this.mFavourite = mFavourite;
    }

    public String getmNotes() {
        return mNotes;
    }

    public void setmNotes(String mNotes) {
        this.mNotes = mNotes;
    }

    public void debugLog(){
        Log.d("Location", "" + mName + " " + mWikiUrl);
    }
}
