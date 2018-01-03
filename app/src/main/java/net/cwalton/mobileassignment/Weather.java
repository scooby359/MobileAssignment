package net.cwalton.mobileassignment;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
    private static String cityName;
    private static String temp;
    private static String condition;
    private static int conditionCode;
    private static int icon;

    private static final String API = "&APPID=";
    private static final String API_KEY = "4e58a95dd52b09ca48d9e6f3d362bc8c";

    private static final String CENT = "&units=metric";
    private static final String FAR = "&units=imperial";
    private String format;

    private static final String CENT_SYMBOL = "°c";
    private static final String FAR_SYMBOL = "°f";

    private static final String DAILY_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    private static final String THREE_DAYS = "&cnt=3";
    private static final String ONE_DAY = "&cnt=1";

    private static final String CURRENT_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String LAT = "lat=";
    private static final String LON = "&lon=";

    //Build URL from location data
    public String buildCurrentLocationWeatherUrl(Location location, Boolean useCelsius){

        if (useCelsius){
            format = CENT;
        }else{
            format = FAR;
        }

        String url = CURRENT_BASE_URL + LAT + location.getLatitude() + LON + location.getLongitude() + format + API + API_KEY;
        Log.d("Weather","Url = " + url);
        return url;
    }

    //Build url from City name
    public String buildCityWeatherUrl(String city, String country, Boolean useCelsius){

        if (useCelsius){
            format = CENT;
        }else{
            format = FAR;
        }

        try {
            city = URLEncoder.encode(city, "UTF-8");
            country = URLEncoder.encode(country, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = CURRENT_BASE_URL + "q=" + city + "," + country + format + API + API_KEY;

        Log.d("Weather","Url = " + url);
        return url;
    }

    public void getThreeDayWeather(){
        //todo - 3 day forecast for location
    }

    //http://android-er.blogspot.co.uk/2015/10/android-query-current-weather-using.html
    //Parse JSON response and save details
    public void parseCurrentWeatherResponse(JSONObject response, Boolean celsius){

        String name = null;
        String temp = null;
        String condition = null;
        String code = null;

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
                        code = jsonGetString(weatherObj, "id");
                    }

                }

                Log.d("Weather", "Name = " + name + ". Temp = " + temp + ". Condition = " + condition);

                //Convert to int and back to string to lose decimal value easily
                float tempFloat = Float.parseFloat(temp);
                int tempInt = Math.round(tempFloat);
                if (celsius){
                    Weather.temp = Integer.toString(tempInt) + CENT_SYMBOL;
                }else {
                    Weather.temp = Integer.toString(tempInt) + FAR_SYMBOL;
                }
                cityName = name;
                Weather.condition = condition;
                conditionCode = Integer.parseInt(code);
                setIcon(conditionCode);
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

    //Set weather icon from condition code
    private void setIcon(int code){

        if (code >= 200 && code <= 232){
            icon = R.drawable.ic_weather_lightning;
        }else if (code >= 300 && code <= 321){
            icon = R.drawable.ic_weather_rainy;
        }else if (code >= 500 && code <= 531){
            icon = R.drawable.ic_weather_pouring;
        }else if (code >= 600 && code <= 622){
            icon = R.drawable.ic_weather_snowy;
        }else if (code >= 701 && code <= 781){
            icon = R.drawable.ic_weather_fog;
        }else if (code == 800){
            icon = R.drawable.ic_weather_sunny;
        }else if (code >= 801 && code <= 804){
            icon = R.drawable.ic_weather_cloudy;
        }else if (code >= 900 && code <= 906){
            icon = R.drawable.ic_weather_lightning;
        }else{
            Log.d(LOG_TAG,"Condition unknown - defaulting icon");
            icon = R.drawable.ic_weather_lightning;
        }
    }

    public String getmCityName() {
        return cityName;
    }

    public String getmCondition() {
        return condition;
    }

    public String getmTemp() {
        return temp;
    }

    public int getmIcon() {
        return icon;
    }
}



