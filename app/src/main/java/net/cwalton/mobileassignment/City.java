package net.cwalton.mobileassignment;

/**
 * Created by scoob on 24/12/2017.
 */

public class City extends Location {

    private String mCountry;
    private String mPopulation;
    private String mAirport;

    public City(String mName, String mType, String mWikiUrl, String country, String population, String airport) {
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
