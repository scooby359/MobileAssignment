package net.cwalton.mobileassignment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by scoob on 24/12/2017.
 * Template for City fragment activities
 */

public class FragmentCity extends Fragment {

    private static final String LOG_TAG = "FragmentCity";

    private Context mContext;

    private TravelDB db;
    private City mCity;
    private String mLocationArg;

    private TextView tv_name;
    private ImageButton ib_favourite;
    private ImageButton ib_wiki;
    private ImageButton ib_notes;
    private ImageButton ib_airport;
    private CardView cv_notes;
    private CardView cv_weather;
    private TextView tv_notes;
    private TextView tv_population;
    private TextView tv_airport;
    private ImageButton ib_map;
    private TextView tv_country;
    private TextView tv_weather_cond;
    private TextView tv_weather_temp;
    private ImageView iv_weather_icon;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_city, container, false);
        mLocationArg = getArguments().getString(Location.FRAG_LOCATION_NAME);

        db = new TravelDB(mContext);
        mCity = db.getCity(mLocationArg);

        tv_name = view.findViewById(R.id.tv_city_name);
        ib_favourite = view.findViewById(R.id.ib_city_favourite);
        ib_wiki = view.findViewById(R.id.ib_city_wiki);
        ib_notes = view.findViewById(R.id.ib_city_notes);
        cv_notes = view.findViewById(R.id.cv_city_notes);
        tv_notes = view.findViewById(R.id.tv_city_notes);
        cv_weather = view.findViewById(R.id.cv_city_weather);
        tv_population = view.findViewById(R.id.tv_city_population);
        tv_airport = view.findViewById(R.id.tv_city_airport);
        ib_airport = view.findViewById(R.id.ib_city_airport);
        ib_map = view.findViewById(R.id.ib_city_map);
        tv_country = view.findViewById(R.id.tv_city_ofcountry);
        cv_weather = view.findViewById(R.id.cv_city_weather);
        tv_weather_cond = view.findViewById(R.id.tv_city_weather_type);
        tv_weather_temp = view.findViewById(R.id.tv_city_weather_temp);
        iv_weather_icon = view.findViewById(R.id.iv_city_weather_icon);

        tv_name.setText(mCity.getmName());
        tv_population.setText(mCity.getmPopulation());
        tv_airport.setText(mCity.getmAirport());
        tv_country.setText(mCity.getmCountry());

        cv_weather.setVisibility(View.GONE);

        updateNotesCard();

        updateFavIcon();

        ib_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavourite();
            }
        });

        ib_wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage();
            }
        });

        ib_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotes();
            }
        });

        ib_airport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapsAirport();
            }
        });

        ib_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaps();
            }
        });


        //Weather bit
        final Weather weatherHelper = new Weather();
        String url = weatherHelper.buildCityWeather(mCity.getmName(), mCity.getmCountry());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest weatherRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "JSON Response = " + response);
                        cv_weather.setVisibility(View.VISIBLE);
                        weatherHelper.parseCurrentWeatherResponse(response);
                        tv_weather_cond.setText(weatherHelper.getmCondition());
                        //todo - allow user to select farenheit
                        tv_weather_temp.setText(weatherHelper.getmTemp()+"°c");
                        iv_weather_icon.setImageDrawable(getResources().getDrawable(weatherHelper.getmIcon()));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(LOG_TAG, "JSON Response Error returned");
                    }
                });
        queue.add(weatherRequest);

        return view;
    }

    public void toggleFavourite(){
        String response = db.toggleCityFavourite(mLocationArg);
        if (response.equals(Location.LOC_FAV_TRUE)){
            mCity.setmFavourite(Location.LOC_FAV_TRUE);
        }else {
            mCity.setmFavourite(Location.LOC_FAV_FALSE);
        }
        updateFavIcon();
        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
    }

    public void updateFavIcon(){
        if (mCity.getmFavourite().equals(Location.LOC_FAV_TRUE)){
            ib_favourite.setImageResource(R.drawable.ic_star_white_24dp);
        }else{
            ib_favourite.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
    }

    public void openWebPage(){

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getActivity(), Uri.parse(mCity.getmWikiUrl()));
    }

    public void openNotes(){

        //todo - use custom layout so margins can be set
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText et_notes = new EditText(getActivity());
        et_notes.setText(mCity.getmNotes());

        builder.setView(et_notes);
        String title = getString(R.string.notes_title) + " " + mCity.getmName();
        builder.setTitle(title);
        builder.setPositiveButton(R.string.notes_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String userInput = et_notes.getText().toString();
                db.updateCityNotes(mLocationArg, userInput);
                mCity.setmNotes(userInput);
                updateNotesCard();
            }})
                .setNegativeButton(R.string.notes_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        confirmDeleteNote();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void confirmDeleteNote(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.notes_delete_confirm)
                .setPositiveButton(R.string.notes_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.updateCityNotes(mLocationArg, "");
                        mCity.setmNotes("");
                        updateNotesCard();
                    }
                })
                .setNegativeButton(R.string.notes_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openNotes();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateNotesCard(){
        tv_notes.setText(mCity.getmNotes());
        if (mCity.getmNotes().equals("")){
            cv_notes.setVisibility(View.INVISIBLE);
        }else{
            cv_notes.setVisibility(View.VISIBLE);
        }
    }

    //    private static final String MAPS_URL = "www.google.com/maps/search/";
    //https://www.google.com/maps/search/?api=1&query=centurylink+field

    public void openMapsAirport(){

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + mCity.getmAirport()));
        Log.d(LOG_TAG, "Url = " + intent.getData());
        startActivity(intent);
    }

    public void openMaps(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + mCity.getmName()));
        Log.d(LOG_TAG, "Url = " + intent.getData());
        startActivity(intent);
    }

}
