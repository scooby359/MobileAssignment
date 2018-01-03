package net.cwalton.mobileassignment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by scoob on 24/12/2017.
 * Template for City fragment activities
 */

public class FragmentCity extends Fragment {

    private static final String LOG_TAG = "FragmentCity";

    private Context context;
    private TravelDB db;
    private City city;
    private String locationArg;
    private SharedPreferences sharedPref;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Setup view and get location information
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        locationArg = getArguments().getString(Location.FRAG_LOCATION_NAME);
        db = new TravelDB(context);
        city = db.getCity(locationArg);

        //Get refs to all layout objects
        final TextView tvName = view.findViewById(R.id.tv_city_name);
        final ImageButton ibFavourite = view.findViewById(R.id.ib_city_favourite);
        final ImageButton ibWiki = view.findViewById(R.id.ib_city_wiki);
        final ImageButton ibNotes = view.findViewById(R.id.ib_city_notes);
        final CardView cvNotes = view.findViewById(R.id.cv_city_notes);
        final TextView tvNotes = view.findViewById(R.id.tv_city_notes);
        final CardView cvWeather = view.findViewById(R.id.cv_city_weather);
        final TextView tvPopulation = view.findViewById(R.id.tv_city_population);
        final TextView tvAirport = view.findViewById(R.id.tv_city_airport);
        final ImageButton ibAirport = view.findViewById(R.id.ib_city_airport);
        final ImageButton ibMap = view.findViewById(R.id.ib_city_map);
        final TextView tvCountry = view.findViewById(R.id.tv_city_ofcountry);
        final TextView tvWeatherCond = view.findViewById(R.id.tv_city_weather_type);
        final TextView tvWeatherTemp = view.findViewById(R.id.tv_city_weather_temp);
        final ImageView iv_weather_icon = view.findViewById(R.id.iv_city_weather_icon);

        //Populate layout objects
        tvName.setText(city.getmName());
        tvPopulation.setText(city.getmPopulation());
        tvAirport.setText(city.getmAirport());
        tvCountry.setText(city.getmCountry());

        cvWeather.setVisibility(View.GONE);

        updateNotesCard(cvNotes, tvNotes);
        updateFavIcon(ibFavourite);

        //Set onclick listeners
        ibFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavourite(ibFavourite);
            }
        });

        ibWiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage();
            }
        });

        ibNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotes(cvNotes, tvNotes);
            }
        });

        ibAirport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapsAirport();
            }
        });

        ibMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaps();
            }
        });


        //Request weather data and display to screen
        final Boolean useCelsius = sharedPref.getBoolean(getResources().getString(R.string.temp_key_celsius),true);
        final Weather weatherHelper = new Weather();
        String url = weatherHelper.buildCityWeatherUrl(city.getmName(), city.getmCountry(), useCelsius);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest weatherRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "JSON Response = " + response);
                        cvWeather.setVisibility(View.VISIBLE);
                        weatherHelper.parseCurrentWeatherResponse(response, useCelsius);
                        tvWeatherCond.setText(weatherHelper.getmCondition());
                        tvWeatherTemp.setText(weatherHelper.getmTemp());
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

    //Checks favourite status and updates database
    public void toggleFavourite(ImageButton button){
        String response = db.toggleCityFavourite(locationArg);
        if (response.equals(Location.LOC_FAV_TRUE)){
            city.setmFavourite(Location.LOC_FAV_TRUE);
        }else {
            city.setmFavourite(Location.LOC_FAV_FALSE);
        }
        updateFavIcon(button);
    }

    //Updates favourite icon
    public void updateFavIcon(ImageButton button){
        if (city.getmFavourite().equals(Location.LOC_FAV_TRUE)){
            //ib_favourite.setImageResource(R.drawable.ic_star_white_24dp);
            button.setImageResource(R.drawable.ic_star_white_24dp);
        }else{
            //ib_favourite.setImageResource(R.drawable.ic_star_border_white_24dp);
            button.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
    }

    //Open custom Chrome tab to location Wikipedia page
    public void openWebPage(){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getActivity(), Uri.parse(city.getmWikiUrl()));
    }

    //Opens a dialog popup to view, edit and delete notes
    public void openNotes(final CardView cardView, final TextView notes){
        //todo - use custom layout so margins can be set
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText etNotes = new EditText(getActivity());
        etNotes.setText(city.getmNotes());

        builder.setView(etNotes);
        String title = getString(R.string.notes_title) + " " + city.getmName();
        builder.setTitle(title);
        builder.setPositiveButton(R.string.notes_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String userInput = etNotes.getText().toString();
                db.updateCityNotes(locationArg, userInput);
                city.setmNotes(userInput);
                updateNotesCard(cardView, notes);
            }})
                .setNegativeButton(R.string.notes_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        confirmDeleteNote(cardView, notes);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Opens a dialog to confirm user wants to delete notes then updates db
    public void confirmDeleteNote(final CardView cardView, final TextView notes){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.notes_delete_confirm)
                .setPositiveButton(R.string.notes_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.updateCityNotes(locationArg, "");
                        city.setmNotes("");
                        updateNotesCard(cardView, notes);
                    }
                })
                .setNegativeButton(R.string.notes_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openNotes(cardView, notes);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Updates notes card to display notes, or hide card if none saved
    public void updateNotesCard(CardView cardView, TextView notes){
        notes.setText(city.getmNotes());
        if (city.getmNotes().equals("")){
            cardView.setVisibility(View.INVISIBLE);
        }else{
            cardView.setVisibility(View.VISIBLE);
        }
    }

    //Opens Airport in Google Maps
    public void openMapsAirport(){

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + city.getmAirport()));
        Log.d(LOG_TAG, "Url = " + intent.getData());
        startActivity(intent);
    }

    //Opens location in Google Maps
    public void openMaps(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + city.getmName()));
        Log.d(LOG_TAG, "Url = " + intent.getData());
        startActivity(intent);
    }

}
