package net.cwalton.mobileassignment;

/**
 * Created by scoob on 24/12/2017.
 */

public class City extends Location {

    private String mCountry;
    private int mPopulation;
    private String mAirport;

    public City(String mName, locationType mType, String mWikiUrl, String country, int population, String airport) {
        super(mName, mType, mWikiUrl);
        this.mCountry = country;
        this.mPopulation = population;
        this.mAirport = airport;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public int getmPopulation() {
        return mPopulation;
    }

    public void setmPopulation(int mPopulation) {
        this.mPopulation = mPopulation;
    }

    public String getmAirport() {
        return mAirport;
    }

    public void setmAirport(String mAirport) {
        this.mAirport = mAirport;
    }
}
