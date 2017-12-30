package net.cwalton.mobileassignment;

/**
 * Created by scoob on 24/12/2017.
 * Template for City Class, inheriting from Location super class
 */

public class City extends Location {

    private String mCountry;
    private String mPopulation;
    private String mAirport;

    public City(String mName, String mType, String mWikiUrl, String mFavourite, String mNotes, String mCountry, String mPopulation, String mAirport) {
        super(mName, mType, mWikiUrl, mFavourite, mNotes);
        this.mCountry = mCountry;
        this.mPopulation = mPopulation;
        this.mAirport = mAirport;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmPopulation() {
        return mPopulation;
    }

    public void setmPopulation(String mPopulation) {
        this.mPopulation = mPopulation;
    }

    public String getmAirport() {
        return mAirport;
    }

    public void setmAirport(String mAirport) {
        this.mAirport = mAirport;
    }
}
