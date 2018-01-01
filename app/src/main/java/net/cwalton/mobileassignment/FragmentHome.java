package net.cwalton.mobileassignment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.net.URL;

/**
 * Created by scoob on 15/12/2017.
 * Fragment for home screen
 */

public class FragmentHome extends Fragment {

    private static final String LOG_TAG = "FragmentHome";
    private Context mContext;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private int mHaveLocPermission;
    private Weather mWeather;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mHaveLocPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);

        mWeather = new Weather(mContext);

        TextView tv_status = (TextView) view.findViewById(R.id.tv_weather_status);

        //todo remove this
        if (mHaveLocPermission == 0)
        {
            tv_status.setText("Permission OK");
        }else{
            tv_status.setText("Permission denied");
        }

        getActivity();

        if (mHaveLocPermission == PackageManager.PERMISSION_GRANTED){

            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
            mFusedLocationProviderClient.getLastLocation() //get location
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<android.location.Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location == null) {
                                Log.d(LOG_TAG,"no location");
                                        //handle no location known
                                //adjust card displayed on screen
                            }else{
                                Log.d(LOG_TAG,"location found");
                                Log.d(LOG_TAG, "Lat = " + location.getLatitude() + " loc = " + location.getLongitude());
                                //handle weather request then update screen on result
                                //need to do async?
                                RequestWeather requestWeather = new RequestWeather();
                                requestWeather.doInBackground(location);
                            }
                        }
                    });
        }else{
            Log.d(LOG_TAG, "Permission wasn't obtained so not requesting location");
                    //handle no loc permission - eg change display
        }



        return view;
    }

    private class RequestWeather extends AsyncTask<Location, Void, > {
        @Override
        protected Void doInBackground(Location... locations) {
            mWeather.getCurrentLocWeather(locations[0]);
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(LOG_TAG, "PostExecute called");

        }
    }



}
