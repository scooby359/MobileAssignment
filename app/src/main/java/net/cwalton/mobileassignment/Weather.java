package net.cwalton.mobileassignment;

import android.app.Activity;
import android.content.Context;
import android.location.*;
import android.location.Location;
import android.util.Log;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import net.cwalton.mobileassignment.R;

/**
 * Created by scoob on 31/12/2017.
 * Ref - http://android-er.blogspot.co.uk/2015/10/android-query-current-weather-using.html
 * For parsing JSON data
 *
 * https://materialdesignicons.com/
 *For weather icons
 */

public class Weather {

    private static final String LOG_TAG = "Weather";
    private static String mCityName;
    private static String mTemp;
    private static String mCondition;
    private static int mIcon;

    private static final String API = "&APPID=";
    private static final String API_KEY = "4e58a95dd52b09ca48d9e6f3d362bc8c";

    private static final String CENT = "&units=metric";
    private static final String FAR = "&units=imperial";

    private static final String DAILY_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    private static final String THREE_DAYS = "&cnt=3";
    private static final String ONE_DAY = "&cnt=1";

    private static final String CURRENT_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String LAT = "lat=";
    private static final String LON = "&lon=";

    public String buildCurrentWeatherUrl(Location location){
        String url = CURRENT_BASE_URL + LAT + location.getLatitude() + LON + location.getLongitude() + CENT + API + API_KEY;
        Log.d("Weather","Url = " + url);
        return url;
    };

    public void getThreeDayWeather(){
        //todo - 3 day forecast for location
    }

    //http://android-er.blogspot.co.uk/2015/10/android-query-current-weather-using.html
    public void parseCurrentWeatherResponse(JSONObject response){

        String name = null;
        String temp = null;
        String condition = null;

        String cod = jsonGetString(response, "cod");
        if (cod!=null){
            if (cod.equals("200")){

                name = jsonGetString(response, "name");

                JSONObject main = jsonGetJsonObject(response, "main");
                if (main!=null){
                    temp = jsonGetString(main, "temp");
                }

                JSONArray weather = jsonGetJsonArray(response, "weather");
                if (weather!=null){

                    JSONObject weatherObj = null;

                    try {
                        weatherObj = weather.getJSONObject(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (weatherObj!=null){
                        condition = jsonGetString(weatherObj, "main");
                    }

                }

                Log.d("Weather", "Name = " + name + ". Temp = " + temp + ". Condition = " + condition);

                mCityName = name;
                mTemp = temp;
                mCondition = condition;
                setIcon(condition);
            }
        }
    }

    private String jsonGetString(JSONObject json, String param){
        String response = null;
        try {
            response = json.getString(param);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    private JSONObject jsonGetJsonObject(JSONObject json, String param){
        JSONObject jsonObject = null;
        try {
            jsonObject = json.getJSONObject(param);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }

    private JSONArray jsonGetJsonArray(JSONObject json, String param){
        JSONArray jsonArray = null;
        try {
            jsonArray = json.getJSONArray(param);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;

    }

    private void setIcon(String condition){

        if (condition.equals("Thunderstorm")){
            mIcon = R.drawable.ic_weather_lightning;
        }else if (condition.equals("Drizzle")){
            mIcon = R.drawable.ic_weather_rainy;
        }else if (condition.equals("Rain")){
            mIcon = R.drawable.ic_weather_pouring;
        }else if (condition.equals("Snow")){
            mIcon = R.drawable.ic_weather_snowy;
        }else if (condition.equals("Atmosphere")){
            mIcon = R.drawable.ic_weather_fog;
        }else if (condition.equals("Clear")){
            mIcon = R.drawable.ic_weather_sunny;
        }else if (condition.equals("Clouds")){
            mIcon = R.drawable.ic_weather_cloudy;
        }else if (condition.equals("Extreme")){
            mIcon = R.drawable.ic_weather_lightning;
        }else{
            Log.d(LOG_TAG,"Condition unknown - defaulting icon");
            mIcon = R.drawable.ic_weather_lightning;
        }
    }



    public String getmCityName() {
        return mCityName;
    }
    public String getmCondition() {
        return mCondition;
    }

    public String getmTemp() {
        return mTemp;
    }

    public int getmIcon() {
        return mIcon;
    }
}



