package net.cwalton.mobileassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by scoob on 24/12/2017.
 *
 * Refs:
 * Boyer, R., Mew, K., 2016. Android Application Development Cookbook. 2nd ed. Birmingham: Packt Publishing.
 *  - Used for structuring setup of the database - onCreate method.
 */

public class TravelDB extends SQLiteOpenHelper {

    private static final String LOG_TAG = "TravelDB";

    public static final String DATABASE_NAME = "travelDB";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_COUNTRIES = "countries";
    public static final String COLUMN_COUNTY_NAME = "country_name";
    public static final String COLUMN_COUNTRY_TYPE = "country_type";
    public static final String COLUMN_COUNTRY_URL = "country_url";
    public static final String COLUMN_COUNTRY_CURRENCY = "country_currency";
    public static final String COLUMN_COUNTRY_LANGUAGE = "country_language";
    public static final String COLUMN_COUNTRY_CAPITAL = "country_capital";
    public static final String COLUMN_COUNTRY_FAVOURITE = "country_favourite";
    public static final String COLUMN_COUNTRY_NOTES = "country_notes";

    public static final String TABLE_CITIES = "cities";
    public static final String COLUMN_CITY_NAME = "city_name";
    public static final String COLUMN_CITY_TYPE = "city_type";
    public static final String COLUMN_CITY_URL = "city_url";
    public static final String COLUMN_CITY_COUNTRY = "city_country";
    public static final String COLUMN_CITY_POPULATION = "city_population";
    public static final String COLUMN_CITY_AIRPORT = "city_airport";
    public static final String COLUMN_CITY_FAVOURITE = "city_favourite";
    public static final String COLUMN_CITY_NOTES = "city_notes";

    public TravelDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //(Boyer and Mew, 2016)
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create Countries table
        sqLiteDatabase.execSQL("" +
                "CREATE TABLE " + TABLE_COUNTRIES + "(" +
                "_id integer PRIMARY KEY," +
                COLUMN_COUNTY_NAME + " TEXT, " +
                COLUMN_COUNTRY_TYPE + " TEXT," +
                COLUMN_COUNTRY_URL + " TEXT," +
                COLUMN_COUNTRY_CURRENCY + " TEXT," +
                COLUMN_COUNTRY_LANGUAGE + " TEXT," +
                COLUMN_COUNTRY_CAPITAL + " TEXT," +
                COLUMN_COUNTRY_FAVOURITE + " TEXT," +
                COLUMN_COUNTRY_NOTES + " TEXT" +
                ");");

        //Create Cities table
        sqLiteDatabase.execSQL("" +
                "CREATE TABLE " + TABLE_CITIES + "(" +
                "_id integer PRIMARY KEY," +
                COLUMN_CITY_NAME + " TEXT," +
                COLUMN_CITY_TYPE + " TEXT," +
                COLUMN_CITY_URL + " TEXT," +
                COLUMN_CITY_COUNTRY + " TEXT," +
                COLUMN_CITY_POPULATION + " TEXT," +
                COLUMN_CITY_AIRPORT + " TEXT," +
                COLUMN_CITY_FAVOURITE + " TEXT," +
                COLUMN_CITY_NOTES + " TEXT" +
                ");");

        //Create instance of initial data class and get arrays of countries and cities from it
        InitialData data = new InitialData();
        Country[] countries = data.getInitialCountries();
        City[] cities = data.getInitialCities();

        //Populate DB with initial data
        //populate country data
        for (int i = 0; i < data.getNUM_COUNTRIES(); i++){

            ContentValues values = new ContentValues();
            values.put(COLUMN_COUNTY_NAME, countries[i].getmName());
            values.put(COLUMN_COUNTRY_TYPE, countries[i].getmType());
            values.put(COLUMN_COUNTRY_URL, countries[i].getmWikiUrl());
            values.put(COLUMN_COUNTRY_CURRENCY, countries[i].getmCurrency());
            values.put(COLUMN_COUNTRY_LANGUAGE, countries[i].getmLanguage());
            values.put(COLUMN_COUNTRY_CAPITAL, countries[i].getmCapital());
            values.put(COLUMN_COUNTRY_FAVOURITE, Location.LOC_FAV_FALSE);
            values.put(COLUMN_COUNTRY_NOTES, "");
            long id = sqLiteDatabase.insert(TABLE_COUNTRIES, null, values);
            if (id >= 0){
                Log.d(LOG_TAG, "Country record created. ID: " + id);
            }
            else{
                Log.d(LOG_TAG, "Error creating country record");
            }

        }

        //Populate city data
        for (int i = 0; i < data.getNUM_CITIES(); i++){
            ContentValues values = new ContentValues();
            values.put(COLUMN_CITY_NAME, cities[i].getmName());
            values.put(COLUMN_CITY_TYPE, cities[i].getmType());
            values.put(COLUMN_CITY_URL, cities[i].getmWikiUrl());
            values.put(COLUMN_CITY_COUNTRY, cities[i].getmCountry());
            values.put(COLUMN_CITY_POPULATION, cities[i].getmPopulation());
            values.put(COLUMN_CITY_AIRPORT, cities[i].getmAirport());
            values.put(COLUMN_CITY_FAVOURITE, Location.LOC_FAV_FALSE);
            values.put(COLUMN_CITY_NOTES, "");
            long id = sqLiteDatabase.insert(TABLE_CITIES, null, values);
            if (id >= 0){
                Log.d(LOG_TAG, "City record created. ID: " + id);
            }
            else{
                Log.d(LOG_TAG, "Error creating city record");
            }
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void testDatabase(){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        /*
        String query = "SELECT * FROM " + TABLE_COUNTRIES + ";";

        Cursor response =  sqLiteDatabase.rawQuery(query, null);
        response.moveToFirst();

        int index = response.getColumnIndexOrThrow(COLUMN_COUNTY_NAME);
        String name = response.getString(index);

        index = response.getColumnIndexOrThrow(COLUMN_COUNTRY_TYPE);
        String type = response.getString(index);

        index = response.getColumnIndexOrThrow(COLUMN_COUNTRY_URL);
        String url = response.getString(index);

        index = response.getColumnIndexOrThrow(COLUMN_COUNTRY_CURRENCY);
        String currency = response.getString(index);

        index = response.getColumnIndexOrThrow(COLUMN_COUNTRY_LANGUAGE);
        String language = response.getString(index);

        index = response.getColumnIndexOrThrow(COLUMN_COUNTRY_CAPITAL);
        String capital = response.getString(index);

        Country test = new Country(name, type, url, currency, language, capital);
        test.printToLog();
        */

    }

    public void saveCountry(Country country){
        //todo add savecountry method

    }

    public void saveCity(City city){
        //todo add save city method
    }

    public Country getCountry(){
        //todo add getcountry method
        return null;
    }

    public City getCity(){
        //todo add cetgity method

        return null;
    }

    public Country[] getAllCountries(){
        //todo add all countries method
        return null;
    }

    public Country[] getFavouriteCountries(){
        //todo add all fave countries method
        return null;
    }

    public City[] getAllCities(){
        //todo add all cities method
        return null;
    }

    public City[] getFavouriteCities(){
        //todo add all fave cities method
        return null;
    }

    public void updateCountryNotes(Country country){
        //todo update country notes
    }

    public void updateCityNotes(City city){
        //todo update city notes
    }

    public void toggleCityFavourite(City city){
        //todo toggle city fave
    }

    public void toggleCountryFavourite(Country country){
        //todo toggle country fave
    }

}
