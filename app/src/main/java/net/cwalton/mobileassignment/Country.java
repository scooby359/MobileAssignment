package net.cwalton.mobileassignment;

import android.util.Log;

/**
 * Created by scoob on 24/12/2017.
 * Template for Country class, inheriting from Location super class
 */

public class Country extends Location {

    private static final String LOG_TAG = "Country";

    private String mCurrency;
    private String mLanguage;
    private String mCapital;
    private String[] mCities;
    private String mCountryCode;


    public Country(String mName, String mType, String mWikiUrl, String mFavourite, String mNotes, String mCurrency, String mLanguage, String mCapital, String[] mCities, String mCountryCode) {
        super(mName, mType, mWikiUrl, mFavourite, mNotes);
        this.mCurrency = mCurrency;
        this.mLanguage = mLanguage;
        this.mCapital = mCapital;
        this.mCities = mCities;
        this.mCountryCode = mCountryCode;
    }

    public String getmCurrency() {
        return mCurrency;
    }

    public String getmLanguage() {
        return mLanguage;
    }

    public String getmCapital() {
        return mCapital;
    }

    public String getmCountryCode() {
        return mCountryCode;
    }

    public String[] getmCities() {
        return mCities;
    }

    public void setmCities(String[] mCities) {
        this.mCities = mCities;
    }

}

