package net.cwalton.mobileassignment;

/**
 * Created by scoob on 24/12/2017.
 * Template for City Class, inheriting from Location super class
 */

public class City extends Location {

    private final String country;
    private final String population;
    private final String airport;

    public City(String mName, String mType, String mWikiUrl, String mFavourite, String mNotes, String country, String population, String airport) {
        super(mName, mType, mWikiUrl, mFavourite, mNotes);
        this.country = country;
        this.population = population;
        this.airport = airport;
    }

    public String getCountry() {
        return country;
    }

    public String getPopulation() {
        return population;
    }

    public String getAirport() {
        return airport;
    }
}


