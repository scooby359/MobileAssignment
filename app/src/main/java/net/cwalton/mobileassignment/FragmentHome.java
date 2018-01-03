package net.cwalton.mobileassignment;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by scoob on 15/12/2017.
 * Fragment for home screen
 */

public class FragmentHome extends Fragment {

    private static final String LOG_TAG = "FragmentHome";
    private Context context;
    private ActivityComms activityComms;

    private ConstraintLayout lWeatherLoading;
    private ConstraintLayout lWeatherLocError;
    private ConstraintLayout lDataError;
    private ConstraintLayout lWeatherForecast;
    private ImageView ivWeatherIcon;
    private TextView tvWeatherLocation;
    private TextView tvWeatherCondition;
    private TextView tvWeatherTemp;
    private Boolean useCelsius;
    private SharedPreferences sharedPref;

    private List<net.cwalton.mobileassignment.Location> faveLocations;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        activityComms = (ActivityComms)context;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Setup view and get location information
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TravelDB db = new TravelDB(context);
        faveLocations = db.getAllFavourites();

        //Get refs to layout objects
        final CardView cvHomeWeather = view.findViewById(R.id.cv_home_loc_weather);
        final ConstraintLayout clCities = view.findViewById(R.id.cl_cities_panel);
        final ConstraintLayout clCountries = view.findViewById(R.id.cl_country_panel);
        final ListView lvFavourites = view.findViewById(R.id.lv_home_faves);
        final TextView tvAddfaves = view.findViewById(R.id.tv_home_addfaves);

        lWeatherLoading = view.findViewById(R.id.lay_home_weather_loading);
        lWeatherLocError = view.findViewById(R.id.lay_home_weather_loc_error);
        lDataError = view.findViewById(R.id.lay_home_weather_data_error);
        lWeatherForecast = view.findViewById(R.id.lay_home_weather_forecast);
        ivWeatherIcon = view.findViewById(R.id.iv_home_weather_icon);
        tvWeatherLocation = view.findViewById(R.id.tv_home_weather_loc);
        tvWeatherCondition = view.findViewById(R.id.tv_home_weather_type);
        tvWeatherTemp = view.findViewById(R.id.tv_home_weather_temp);

        //Populate main cities and set up click listeners
        if (!faveLocations.isEmpty()){
            Collections.sort(faveLocations, new SortFavourites());
            ListAdapter adapter = new customAdapter(view.getContext());
            lvFavourites.setAdapter(adapter);
            tvAddfaves.setVisibility(View.GONE);
            lvFavourites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = faveLocations.get(i).getName();
                    String type = faveLocations.get(i).getType();
                    Log.d(LOG_TAG, "Clicked = " + name + " + " + type);
                    if (type.equals(net.cwalton.mobileassignment.Location.LOC_TYPE_CITY)){
                        activityComms.onCityListItemSelected(name);
                    }else{
                        activityComms.onCountryListItemSelected(name);
                    }

                }
            });
        }else {
            lvFavourites.setVisibility(View.GONE);
        }

        //Check permission for location services. If permission gained, get weather for location, otherwise hide card
        int haveLocPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (haveLocPermission == PackageManager.PERMISSION_GRANTED){

            //Display loading layout and hide others
            lWeatherLoading.setVisibility(View.VISIBLE);
            lWeatherLocError.setVisibility(View.GONE);
            lDataError.setVisibility(View.GONE);
            lWeatherForecast.setVisibility(View.GONE);

            //Get location then request weather data
            useCelsius = sharedPref.getBoolean(getResources().getString(R.string.temp_key_celsius),true);
            final Weather weatherHelper = new Weather();

            FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<android.location.Location>() {
                        @Override
                        //If location response provided
                        public void onSuccess(Location location) {
                            if (location == null) {
                                Log.d(LOG_TAG,"no location");
                                lWeatherLoading.setVisibility(View.GONE);
                                lWeatherLocError.setVisibility(View.VISIBLE);
                            }else{
                                Log.d(LOG_TAG,"location found");
                                Log.d(LOG_TAG, "Lat = " + location.getLatitude() + " loc = " + location.getLongitude());

                                //Request weather, get response, then update layout with data
                                String url = weatherHelper.buildCurrentLocationWeatherUrl(location, useCelsius);
                                RequestQueue queue = Volley.newRequestQueue(getActivity());
                                JsonObjectRequest weatherRequest = new JsonObjectRequest
                                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.d(LOG_TAG, "JSON Response = " + response);
                                                lWeatherLoading.setVisibility(View.GONE);
                                                lWeatherForecast.setVisibility(View.VISIBLE);
                                                weatherHelper.parseCurrentWeatherResponse(response, useCelsius);
                                                tvWeatherLocation.setText(weatherHelper.getmCityName());
                                                tvWeatherCondition.setText(weatherHelper.getmCondition());
                                                tvWeatherTemp.setText(weatherHelper.getmTemp());
                                                ivWeatherIcon.setImageDrawable(getResources().getDrawable(weatherHelper.getmIcon()));
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d(LOG_TAG, "JSON Response Error returned");
                                                lWeatherLoading.setVisibility(View.GONE);
                                                lDataError.setVisibility(View.VISIBLE);
                                            }
                                        });
                                queue.add(weatherRequest);

                            }
                        }
                    });
        }else{
            Log.d(LOG_TAG, "Permission wasn't obtained so not requesting location");
                    //handle no loc permission - eg change display
            cvHomeWeather.setVisibility(View.GONE);
        }

        //Set on click listeners
        clCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityComms.openCityList();
            }
        });

        clCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityComms.openCountryList();
            }
        });

        return view;
    }

    //Setup custom adapter for listview
    public class customAdapter extends ArrayAdapter<net.cwalton.mobileassignment.Location> {

        public customAdapter(Context context) {
            super(context, -1, faveLocations);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            View rowView = convertView;
            if (rowView == null) {
                rowView = LayoutInflater.from(getContext()).inflate(R.layout.home_fave_card, parent, false);
            }

                final TextView name = rowView.findViewById(R.id.tv_fave_name);
                final ImageView favIcon = rowView.findViewById(R.id.iv_fave_icon);

                name.setText(faveLocations.get(position).getName());
                if (faveLocations.get(position).getType().equals(net.cwalton.mobileassignment.Location.LOC_TYPE_CITY)){
                    favIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_location_city_black_24dp));
                }else{
                    favIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_earth));
                }

                return rowView;

        }
    }

    //Sorts favourite locations by name
    static class SortFavourites implements Comparator<net.cwalton.mobileassignment.Location>{
        public int compare(net.cwalton.mobileassignment.Location one, net.cwalton.mobileassignment.Location two){
            return one.getName().compareTo(two.getName());
        }
    }
}

