package net.cwalton.mobileassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by scoob on 24/12/2017.
 *
 * Refs:
 * Boyer, R., Mew, K., 2016. Android Application Development Cookbook. 2nd ed. Birmingham: Packt Publishing.
 *  - Used for structuring setup of the database - onCreate method.
 */

public class TravelDB extends SQLiteOpenHelper {

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

        sqLiteDatabase.execSQL("" +
                "CREATE TABLE " + TABLE_CITIES + "(" +
                "_id integer PRIMARY KEY," +
                COLUMN_CITY_NAME + " TEXT," +
                COLUMN_CITY_TYPE + " TEXT," +
                COLUMN_CITY_URL + " TEXT," +
                COLUMN_CITY_COUNTRY + " TEXT," +
                COLUMN_CITY_POPULATION + " integer," +
                COLUMN_CITY_AIRPORT + " TEXT," +
                COLUMN_CITY_FAVOURITE + " TEXT," +
                COLUMN_CITY_NOTES + " TEXT" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveCountry(){
        //todo add savecountry method
    }

    public void saveCity(){
        //todo add save city method
    }

    public void getCountry(){
        //todo add getcountry method
    }

    public void getCity(){
        //todo add cetgity method
    }

    

}
