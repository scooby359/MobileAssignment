package net.cwalton.mobileassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by scoob on 24/12/2017.
 * Database class, holds all DB functions within it, returning city / country objects
 *
 * Refs:
 * Boyer, R., Mew, K., 2016. Android Application Development Cookbook. 2nd ed. Birmingham: Packt Publishing.
 *  - Used for structuring setup of the database - onCreate method.
 */

public class TravelDB extends SQLiteOpenHelper {

    private static final String LOG_TAG = "TravelDB";

    private static final String DATABASE_NAME = "travelDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_COUNTRIES = "countries";
    private static final String COLUMN_COUNTY_NAME = "country_name";
    private static final String COLUMN_COUNTRY_TYPE = "country_type";
    private static final String COLUMN_COUNTRY_URL = "country_url";
    private static final String COLUMN_COUNTRY_CURRENCY = "country_currency";
    private static final String COLUMN_COUNTRY_LANGUAGE = "country_language";
    private static final String COLUMN_COUNTRY_CAPITAL = "country_capital";
    private static final String COLUMN_COUNTRY_FAVOURITE = "country_favourite";
    private static final String COLUMN_COUNTRY_NOTES = "country_notes";

    private static final String TABLE_CITIES = "cities";
    private static final String COLUMN_CITY_NAME = "city_name";
    private static final String COLUMN_CITY_TYPE = "city_type";
    private static final String COLUMN_CITY_URL = "city_url";
    private static final String COLUMN_CITY_COUNTRY = "city_country";
    private static final String COLUMN_CITY_POPULATION = "city_population";
    private static final String COLUMN_CITY_AIRPORT = "city_airport";
    private static final String COLUMN_CITY_FAVOURITE = "city_favourite";
    private static final String COLUMN_CITY_NOTES = "city_notes";

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

    private Country getCountryFromCursor(Cursor data){
        int index = data.getColumnIndexOrThrow(COLUMN_COUNTY_NAME);
        String name = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_COUNTRY_TYPE);
        String type = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_COUNTRY_URL);
        String url = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_COUNTRY_FAVOURITE);
        String favourite = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_COUNTRY_NOTES);
        String notes = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_COUNTRY_CURRENCY);
        String currency = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_COUNTRY_LANGUAGE);
        String language = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_COUNTRY_CAPITAL);
        String capital = data.getString(index);

        Country country = new Country(name, type, url, favourite, notes, currency, language, capital, null);

        return country;
    }

    private City getCityFromCursor(Cursor data){
        int index = data.getColumnIndexOrThrow(COLUMN_CITY_NAME);
        String name = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_CITY_TYPE);
        String type = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_CITY_URL);
        String url = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_CITY_FAVOURITE);
        String favourite = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_CITY_NOTES);
        String notes = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_CITY_COUNTRY);
        String country = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_CITY_POPULATION);
        String population = data.getString(index);

        index = data.getColumnIndexOrThrow(COLUMN_CITY_AIRPORT);
        String airport = data.getString(index);

        City city = new City(name, type, url, favourite, notes, country, population, airport);
        return city;
    }

    //Query the DB with a country name String, returns a country object with all details
    public Country getCountry(String name){

        //get instance of db
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //create query
        String query = "SELECT * FROM " + TABLE_COUNTRIES +
                " WHERE " + COLUMN_COUNTY_NAME + " = " + "\"" + name + "\"" + ";";

        Log.d(LOG_TAG, "DB country query = " + query);

        //make query and store response
        Cursor response =  sqLiteDatabase.rawQuery(query, null);
        response.moveToFirst();

        //Create a country instance from db response
        Country country = getCountryFromCursor(response);

        return country;
    }

    public City getCity(String name){

        //get instance of db
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //create query
        String query = "SELECT * FROM " + TABLE_CITIES +
                " WHERE " + COLUMN_CITY_NAME + " = " + "\"" + name + "\"" + ";";

        Log.d(LOG_TAG, "DB city query = " + query);

        //make query and store response
        Cursor response =  sqLiteDatabase.rawQuery(query, null);
        response.moveToFirst();

        //Create a country instance from db response
        City city = getCityFromCursor(response);

        return city;
    }

    public List<Country> getAllCountries(){
        //get instance of db
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //create query
        String query = "SELECT * FROM " + TABLE_COUNTRIES + " ORDER BY " + COLUMN_COUNTY_NAME + " ASC;";

        Log.d(LOG_TAG, "DB country query = " + query);

        //make query and store response
        Cursor response =  sqLiteDatabase.rawQuery(query, null);
        response.moveToFirst();

        List<Country> countries = new ArrayList<>();

        //For each row in cursor, create a country and add to vector
        for (int i = 0; i < response.getCount(); i++) {
            Country country = getCountryFromCursor(response);
            countries.add(country);
            if (!response.isLast()) {
                response.moveToNext();
            }
        }

        return countries;
    }

    public List<Country> getFavouriteCountries(){
        //todo add all fave countries method
        return null;
    }

    public List<City> getAllCities(){
        //todo add all cities method
        return null;
    }

    public List<City> getFavouriteCities(){
        //todo add all fave cities method
        return null;
    }

    public List<Location> getAllFavourites(){
        //todo add all favourites method
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

    public String toggleCountryFavourite(String countryname){


        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_COUNTRIES +
                " WHERE " + COLUMN_COUNTY_NAME + " = " + "\"" + countryname + "\";";
        Log.d(LOG_TAG, "DB country query = " + query);
        Cursor response =  sqLiteDatabase.rawQuery(query, null);
        response.moveToFirst();

        Country country = getCountryFromCursor(response);
        String querytext = COLUMN_COUNTY_NAME + " = \"" + countryname + "\";";
        Log.d(LOG_TAG, "Fave where query = " + querytext);
        ContentValues values = new ContentValues();
        String newValue;

        if (country.getmFavourite().equals(Location.LOC_FAV_FALSE)){
            values.put(COLUMN_COUNTRY_FAVOURITE, Location.LOC_FAV_TRUE);
            newValue = Location.LOC_FAV_TRUE;
        }else{
            values.put(COLUMN_COUNTRY_FAVOURITE, Location.LOC_FAV_FALSE);
            newValue = Location.LOC_FAV_FALSE;
        }
        int debugResponse = sqLiteDatabase.update(TABLE_COUNTRIES, values, querytext,null );
        Log.d(LOG_TAG, "DB response = " + debugResponse);

        //debug
        query = "SELECT * FROM " + TABLE_COUNTRIES +
                " WHERE " + COLUMN_COUNTY_NAME + " = " + "\"" + countryname + "\";";
        response = sqLiteDatabase.rawQuery(query, null);
        response.moveToFirst();
        Country test = getCountryFromCursor(response);
        test.debugLog();

        return newValue;
    }

    public List<City> getMainCities(String country){

        //get instance of db
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        //create query
        String query = "SELECT * FROM " + TABLE_CITIES +
                " WHERE " + COLUMN_CITY_COUNTRY + " = " + "\"" + country + "\"" +
                " ORDER BY " + COLUMN_CITY_NAME + " ASC;";

        Log.d(LOG_TAG, "DB city query = " + query);

        //make query and store response
        Cursor response =  sqLiteDatabase.rawQuery(query, null);
        response.moveToFirst();

        List<City> cities = new ArrayList<>();

        for (int i = 0; i < response.getCount(); i++) {
            City city = getCityFromCursor(response);
            cities.add(city);
            if (!response.isLast()) {
                response.moveToNext();
            }
        }

        return cities;
    }

    public void initialiseFavourites(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        ContentValues countryValues = new ContentValues();
        countryValues.put(COLUMN_COUNTRY_FAVOURITE, Location.LOC_FAV_FALSE);
        int cityResponse = sqLiteDatabase.update(TABLE_COUNTRIES, countryValues, null,null );

        ContentValues cityValues = new ContentValues();
        cityValues.put(COLUMN_CITY_FAVOURITE, Location.LOC_FAV_FALSE);
        int countryResponse = sqLiteDatabase.update(TABLE_CITIES, cityValues, null,null );

        Log.d(LOG_TAG,"Country Rows set: " + countryResponse);
        Log.d(LOG_TAG,"City Rows set: " + cityResponse);

    }

}
