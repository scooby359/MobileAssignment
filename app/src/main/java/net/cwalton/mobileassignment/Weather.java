package net.cwalton.mobileassignment;

import android.app.Activity;
import android.content.Context;
import android.location.*;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by scoob on 31/12/2017.
 */

public class Weather {

    private static String mCityName;
    private static String mMinTemp;
    private static String mMaxTemp;
    private static String mCondition;
    private static int mIcon;
    private static Context mContext;
    private Location mLocation;

    public Weather(Context context) {

        this.mContext = context;

        //need to figure out how data is coming in to parse

    }

    public static String getmCityName() {
        return mCityName;
    }

    public static String getmMinTemp() {
        return mMinTemp;
    }

    public static String getmMaxTemp() {
        return mMaxTemp;
    }

    public static String getmCondition() {
        return mCondition;
    }

    public static int getmIcon() {
        return mIcon;
    }


        private static final String API = "&APPID=";
        private static final String API_KEY = "4e58a95dd52b09ca48d9e6f3d362bc8c";

        private static final String DAILY_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
        private static final String THREE_DAYS = "&cnt=3";
        private static final String ONE_DAY = "&cnt=1";

        private static final String CURRENT_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
        private static final String LAT = "lat=";
        private static final String LON = "&lon=";





        public void getCurrentLocWeather(android.location.Location location){
            //todo - current location weather
            String url = CURRENT_BASE_URL + LAT + location.getLatitude() + LON + location.getLongitude() + API + API_KEY;
            RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Weather", "Response = " + response);
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Weather", "Error in response");
                }
            });
            queue.add(stringRequest);
        }

        public void getThreeDayWeather(){
            //todo - 3 day forecast for location
        }


    }



