package net.cwalton.mobileassignment;

/**
 * Created by scoob on 24/12/2017.
 */

public class Country extends Location {

    private String mCurrency;
    private String mLanguage;
    private String mCapital;
    private String[] mCities;

    public Country(String mName, locationType mType, String mWikiUrl, String mCurrency, String mLanguage, String mCapital) {
        super(mName, mType, mWikiUrl);
        this.mCurrency = mCurrency;
        this.mLanguage = mLanguage;
        this.mCapital = mCapital;
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
