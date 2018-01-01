package net.cwalton.mobileassignment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

        tv_name = (TextView) view.findViewById(R.id.tv_city_name);
        ib_favourite = (ImageButton) view.findViewById(R.id.ib_city_favourite);
        ib_wiki = (ImageButton) view.findViewById(R.id.ib_city_wiki);
        ib_notes = (ImageButton) view.findViewById(R.id.ib_city_notes);
        cv_notes = (CardView) view.findViewById(R.id.cv_city_notes);
        tv_notes = (TextView) view.findViewById(R.id.tv_city_notes);
        cv_weather = (CardView) view.findViewById(R.id.cv_city_weather);
        tv_population = (TextView) view.findViewById(R.id.tv_city_population);
        tv_airport = (TextView) view.findViewById(R.id.tv_city_airport);
        ib_airport = (ImageButton) view.findViewById(R.id.ib_city_airport);
        ib_map = (ImageButton) view.findViewById(R.id.ib_city_map);
        tv_country = (TextView) view.findViewById(R.id.tv_city_ofcountry);

        tv_name.setText(mCity.getmName());
        tv_population.setText(mCity.getmPopulation());
        tv_airport.setText(mCity.getmAirport());
        tv_country.setText(mCity.getmCountry());

        //todo - city details card breaking - need to make long text wrap

        updateNotesCard();

        updateFavIcon();

        ib_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavourite();
            }
        });

        //todo - better to have the inapp web view instead of opening externally
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
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(mCity.getmWikiUrl()));
        startActivity(intent);
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
