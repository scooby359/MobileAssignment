package net.cwalton.mobileassignment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;

import java.util.List;

/**
 * Created by scoob on 24/12/2017.
 * Template for Country fragment activities
 */

public class FragmentCountry extends Fragment {

    private static final String LOG_TAG = "FragmentCountry";

    private Context context;
    private TravelDB db;
    private Country country;
    private String locationArg;

    private ActivityComms activityComms;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        activityComms = (ActivityComms)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Setup view and get location information
        View view = inflater.inflate(R.layout.fragment_country, container, false);
        locationArg = getArguments().getString(Location.FRAG_LOCATION_NAME);
        db = new TravelDB(context);
        country = db.getCountry(locationArg);
        final List<City> cities = db.getMainCities(locationArg);

        //get refs to layout objects
        final TextView tvName = view.findViewById(R.id.tv_country_name);
        final TextView tvLanguage = view.findViewById(R.id.tv_country_language);
        final TextView tvCurrency = view.findViewById(R.id.tv_country_currency);
        final TextView tvCapital = view.findViewById(R.id.tv_country_capital);
        final ImageButton ibFavourite = view.findViewById(R.id.ib_country_favourite);
        final ImageButton ibWiki = view.findViewById(R.id.ib_country_wiki);
        final ImageButton ibNotes = view.findViewById(R.id.ib_country_notes);
        final CardView cvNotes = view.findViewById(R.id.cv_country_notes);
        final TextView tvNotes = view.findViewById(R.id.tv_country_notes);
        final ImageButton ibMap = view.findViewById(R.id.ib_country_map);
        final TextView tvCity1 = view.findViewById(R.id.tv_country_city1);
        final TextView tvCity2 = view.findViewById(R.id.tv_country_city2);
        final TextView tvCity3 = view.findViewById(R.id.tv_country_city3);
        final TextView tvCity4 = view.findViewById(R.id.tv_country_city4);
        final TextView tvCity5 = view.findViewById(R.id.tv_country_city5);

        //Set properties of layout objects
        tvName.setText(country.getName());
        tvLanguage.setText(country.getLanguage());
        tvCurrency.setText(country.getCurrency());
        tvCapital.setText(country.getCapital());
        updateNotesCard(cvNotes, tvNotes);
        tvCity1.setText(cities.get(0).getName());
        tvCity2.setText(cities.get(1).getName());
        tvCity3.setText(cities.get(2).getName());
        tvCity4.setText(cities.get(3).getName());
        tvCity5.setText(cities.get(4).getName());
        updateFavIcon(ibFavourite);

        //set onclick listeners for buttons
        tvCity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityComms.onCityListItemSelected(cities.get(0).getName());
            }
        });

        tvCity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityComms.onCityListItemSelected(cities.get(1).getName());
            }
        });

        tvCity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityComms.onCityListItemSelected(cities.get(2).getName());
            }
        });

        tvCity4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityComms.onCityListItemSelected(cities.get(3).getName());
            }
        });

        tvCity5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityComms.onCityListItemSelected(cities.get(4).getName());
            }
        });

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

        ibMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaps();
            }
        });
        return view;
    }

    //Checks favourite status and updates database
    private void toggleFavourite(ImageButton button){
        Log.d(LOG_TAG, "Toggle Favourite called");
        String response = db.toggleCountryFavourite(locationArg);
        if (response.equals(Location.LOC_FAV_TRUE)){
            country.setFavourite(Location.LOC_FAV_TRUE);
            Log.d(LOG_TAG,"set fave");
        }else {
            country.setFavourite(Location.LOC_FAV_FALSE);
            Log.d(LOG_TAG,"remove fave");
        }
        updateFavIcon(button);
    }

    //Updates favourite icon
    private void updateFavIcon(ImageButton button){
        Log.d(LOG_TAG,"update fave icon called");
        Log.d(LOG_TAG, "Fav value is " + country.getFavourite());

        if (country.getFavourite().equals(Location.LOC_FAV_TRUE)){
            Log.d(LOG_TAG, "Fav = true");
            button.setImageResource(R.drawable.ic_star_white_24dp);
        }else{
            Log.d(LOG_TAG, "Fav = false");
            button.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
    }

    //Open custom Chrome tab to location Wikipedia page
    private void openWebPage(){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getActivity(), Uri.parse(country.getWikiUrl()));
    }

    //Opens a dialog popup to view, edit and delete notes
    private void openNotes(final CardView card, final TextView notes){

        //todo - use custom layout so margins can be set
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText etNotes = new EditText(getActivity());
        etNotes.setText(country.getNotes());

        builder.setView(etNotes);
        String title = getString(R.string.notes_title) + " " + country.getName();
        builder.setTitle(title);
        builder.setPositiveButton(R.string.notes_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String userInput = etNotes.getText().toString();
                db.updateCountryNotes(locationArg, userInput);
                country.setNotes(userInput);
                updateNotesCard(card, notes);
            }})
            .setNegativeButton(R.string.notes_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
            confirmDeleteNote(card, notes);
            }
                      });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Opens a dialog to confirm user wants to delete notes then updates db
    private void confirmDeleteNote(final CardView card, final TextView notes){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.notes_delete_confirm)
                .setPositiveButton(R.string.notes_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.updateCountryNotes(locationArg, "");
                        country.setNotes("");
                        updateNotesCard(card, notes);
                    }
                })
                .setNegativeButton(R.string.notes_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openNotes(card, notes);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Updates notes card to display notes, or hide card if none saved
    private void updateNotesCard(CardView card, TextView notes){
        notes.setText(country.getNotes());
        if (country.getNotes().equals("")){
            card.setVisibility(View.INVISIBLE);
        }else{
            card.setVisibility(View.VISIBLE);
        }
    }

    //Opens location in Google Maps
    private void openMaps(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + country.getName()));
        Log.d(LOG_TAG, "Url = " + intent.getData());
        startActivity(intent);
    }
}
