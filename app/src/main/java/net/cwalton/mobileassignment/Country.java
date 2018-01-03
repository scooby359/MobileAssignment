package net.cwalton.mobileassignment;

/**
 * Created by scoob on 24/12/2017.
 * Template for Country class, inheriting from Location super class
 */

public class Country extends Location {

    private static final String LOG_TAG = "Country";

    private final String currency;
    private final String language;
    private final String capital;
    private final String[] cities;
    private final String countryCode;


    public Country(String mName, String mType, String mWikiUrl, String mFavourite, String mNotes, String currency, String language, String capital, String[] cities, String countryCode) {
        super(mName, mType, mWikiUrl, mFavourite, mNotes);
        this.currency = currency;
        this.language = language;
        this.capital = capital;
        this.cities = cities;
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public String getLanguage() {
        return language;
    }

    public String getCapital() {
        return capital;
    }

    public String getCountryCode() {
        return countryCode;
    }

}

