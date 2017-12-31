package net.cwalton.mobileassignment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by scoob on 24/12/2017.
 * Template for Country fragment activities
 */

public class FragmentCountry extends Fragment {

    private static final String LOG_TAG = "FragmentCountry";

    private Context mContext;

    private TravelDB db;
    private Country country;
    private String mLocationArg;

    private TextView tv_name;
    private TextView tv_language;
    private TextView tv_currency;
    private TextView tv_capital;
    private TextView tv_maincities;
    private ImageButton ib_favourite;
    private ImageButton ib_wiki;
    private ImageButton ib_notes;
    private CardView cv_notes;
    private TextView tv_notes;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_country, container, false);
        mLocationArg = getArguments().getString(Location.FRAG_LOCATION_NAME);

        db = new TravelDB(mContext);
        country = db.getCountry(mLocationArg);

        tv_name = (TextView) view.findViewById(R.id.tv_country_name);
        tv_language = (TextView) view.findViewById(R.id.tv_country_language);
        tv_currency = (TextView) view.findViewById(R.id.tv_country_currency);
        tv_capital = (TextView) view.findViewById(R.id.tv_country_capital);
        tv_maincities = (TextView) view.findViewById(R.id.tv_country_maincities);
        ib_favourite = (ImageButton) view.findViewById(R.id.ib_country_favourite);
        ib_wiki = (ImageButton) view.findViewById(R.id.ib_country_wiki);
        ib_notes = (ImageButton) view.findViewById(R.id.ib_country_notes);
        cv_notes = (CardView) view.findViewById(R.id.cv_country_notes);
        tv_notes = (TextView) view.findViewById(R.id.tv_country_notes);

        tv_name.setText(country.getmName());
        tv_language.setText(country.getmLanguage());
        tv_currency.setText(country.getmCurrency());
        tv_capital.setText(country.getmCapital());

        updateNotesCard();

        //todo - should probably be interactive to allow jumping straight to city page
        List<City> cities = db.getMainCities(mLocationArg);
        for (int i = 0; i < cities.size(); i++){
            tv_maincities.append(cities.get(i).getmName()+"\n");
        }

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

        return view;
    }

    public void toggleFavourite(){
        Log.d(LOG_TAG, "Toggle Favourite called");
        String response = db.toggleCountryFavourite(mLocationArg);
        if (response.equals(Location.LOC_FAV_TRUE)){
            country.setmFavourite(Location.LOC_FAV_TRUE);
            Log.d(LOG_TAG,"set fave");
        }else {
            country.setmFavourite(Location.LOC_FAV_FALSE);
            Log.d(LOG_TAG,"remove fave");
        }
        updateFavIcon();
        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
    }

    public void updateFavIcon(){
        Log.d(LOG_TAG,"update fave icon called");
        Log.d(LOG_TAG, "Fav value is " + country.getmFavourite());

        if (country.getmFavourite().equals(Location.LOC_FAV_TRUE)){
            Log.d(LOG_TAG, "Fav = true");
            ib_favourite.setImageResource(R.drawable.ic_star_white_24dp);
        }else{
            Log.d(LOG_TAG, "Fav = false");
            ib_favourite.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
    }

    public void openWebPage(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(country.getmWikiUrl()));
        startActivity(intent);
    }

    public void openNotes(){

        //todo - use custom layout so margins can be set
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText et_notes = new EditText(getActivity());
        et_notes.setText(country.getmNotes());

        builder.setView(et_notes);
        String title = getString(R.string.notes_title) + " " + country.getmName();
        builder.setTitle(title);
        builder.setPositiveButton(R.string.notes_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String userInput = et_notes.getText().toString();
                db.updateCountryNotes(mLocationArg, userInput);
                country.setmNotes(userInput);
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
                        db.updateCountryNotes(mLocationArg, "");
                        country.setmNotes("");
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
        tv_notes.setText(country.getmNotes());
        if (country.getmNotes().equals("")){
            cv_notes.setVisibility(View.INVISIBLE);
        }else{
            cv_notes.setVisibility(View.VISIBLE);
        }
    }

}
