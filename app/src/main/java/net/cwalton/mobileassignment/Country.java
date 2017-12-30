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


    public Country(String mName, String mType, String mWikiUrl, String mFavourite, String mNotes, String mCurrency, String mLanguage, String mCapital, String[] mCities) {
        super(mName, mType, mWikiUrl, mFavourite, mNotes);
        this.mCurrency = mCurrency;
        this.mLanguage = mLanguage;
        this.mCapital = mCapital;
        this.mCities = mCities;
    }

    public String getmCurrency() {
        return mCurrency;
    }

    public void setmCurrency(String mCurrency) {
        this.mCurrency = mCurrency;
    }

    public String getmLanguage() {
        return mLanguage;
    }

    public void setmLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }

    public String getmCapital() {
        return mCapital;
    }

    public void setmCapital(String mCapital) {
        this.mCapital = mCapital;
    }

    public String[] getmCities() {
        return mCities;
    }

    public void setmCities(String[] mCities) {
        this.mCities = mCities;
    }

}

