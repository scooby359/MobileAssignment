package net.cwalton.mobileassignment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.net.URL;

/**
 * Created by scoob on 15/12/2017.
 * Fragment for home screen
 */

public class FragmentHome extends Fragment {

    private static final String LOG_TAG = "FragmentHome";
    private Context mContext;
    private ConstraintLayout ml_weather_loading;
    private ConstraintLayout ml_weather_loc_error;
    private ConstraintLayout ml_data_error;
    private ConstraintLayout ml_weather_forecast;
    private CardView cv_home_weather;
    private ImageView miv_weather_icon;
    private TextView mtv_weather_location;
    private TextView mtv_weather_condition;
    private TextView mtv_weather_temp;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cv_home_weather = (CardView) view.findViewById(R.id.cv_home_loc_weather);
        ml_weather_loading = (ConstraintLayout) view.findViewById(R.id.lay_home_weather_loading);
        ml_weather_loc_error = (ConstraintLayout) view.findViewById(R.id.lay_home_weather_loc_error);
        ml_data_error = (ConstraintLayout) view.findViewById(R.id.lay_home_weather_data_error);
        ml_weather_forecast = (ConstraintLayout) view.findViewById(R.id.lay_home_weather_forecast);
        miv_weather_icon = (ImageView) view.findViewById(R.id.iv_home_weather_icon);
        mtv_weather_location = (TextView) view.findViewById(R.id.tv_home_weather_loc);
        mtv_weather_condition = (TextView) view.findViewById(R.id.tv_home_weather_type);
        mtv_weather_temp = (TextView) view.findViewById(R.id.tv_home_weather_temp);

        int haveLocPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (haveLocPermission == PackageManager.PERMISSION_GRANTED){

            //display proper layout
            ml_weather_loading.setVisibility(View.VISIBLE);
            ml_weather_loc_error.setVisibility(View.GONE);
            ml_data_error.setVisibility(View.GONE);
            ml_weather_forecast.setVisibility(View.GONE);

            final Weather weatherHelper = new Weather();
            FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
            mFusedLocationProviderClient.getLastLocation() //get location
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<android.location.Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location == null) {
                                Log.d(LOG_TAG,"no location");
                                ml_weather_loading.setVisibility(View.GONE);
                                ml_weather_loc_error.setVisibility(View.VISIBLE);
                            }else{
                                Log.d(LOG_TAG,"location found");
                                Log.d(LOG_TAG, "Lat = " + location.getLatitude() + " loc = " + location.getLongitude());

                                String url = weatherHelper.buildCurrentWeatherUrl(location);

                                //todo - sort this section out!
                                RequestQueue queue = Volley.newRequestQueue(getActivity());
                                JsonObjectRequest weatherRequest = new JsonObjectRequest
                                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.d(LOG_TAG, "JSON Response = " + response);
                                                ml_weather_loading.setVisibility(View.GONE);
                                                ml_weather_forecast.setVisibility(View.VISIBLE);
                                                weatherHelper.parseCurrentWeatherResponse(response);
                                                mtv_weather_location.setText(weatherHelper.getmCityName());
                                                mtv_weather_condition.setText(weatherHelper.getmCondition());
                                                //todo - allow user to select farenheit
                                                mtv_weather_temp.setText(weatherHelper.getmTemp()+"°c");
                                                miv_weather_icon.setImageDrawable(getResources().getDrawable(weatherHelper.getmIcon()));
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d(LOG_TAG, "JSON Response Error returned");
                                                ml_weather_loading.setVisibility(View.GONE);
                                                ml_data_error.setVisibility(View.VISIBLE);
                                            }
                                        });
                                //Todo - blocked request while fixing layout
                                queue.add(weatherRequest);

                            }
                        }
                    });
        }else{
            Log.d(LOG_TAG, "Permission wasn't obtained so not requesting location");
                    //handle no loc permission - eg change display
            cv_home_weather.setVisibility(View.GONE);
        }

        return view;
    }



}

