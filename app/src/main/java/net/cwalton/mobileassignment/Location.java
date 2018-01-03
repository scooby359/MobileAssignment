package net.cwalton.mobileassignment;

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
    public static final String FRAG_LOCATION_NAME = "frag_location_name";


    private final String name;
    private final String type;
    private final String wikiUrl;
    private String favourite;
    private String notes;

    public Location(String name, String type, String wikiUrl, String favourite, String notes) {

        this.name = name;
        this.type = type;
        this.wikiUrl = wikiUrl;
        this.favourite = favourite;
        this.notes = notes;

    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
