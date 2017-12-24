package net.cwalton.mobileassignment;

/**
 * Created by scoob on 24/12/2017.
 */

public abstract class Location {

    public enum locationType {country, city};

    private String mName;
    private locationType mType;
    private String mWikiUrl;
    private Boolean mFavourite;
    private String mNotes;

    public Location(String mName, locationType mType, String mWikiUrl) {

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

    public locationType getmType() {
        return mType;
    }

    public void setmType(locationType mType) {
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
}
