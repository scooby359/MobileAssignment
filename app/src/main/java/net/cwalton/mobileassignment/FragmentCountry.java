package net.cwalton.mobileassignment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        tv_name.setText(country.getmName());
        tv_language.setText(country.getmLanguage());
        tv_currency.setText(country.getmCurrency());
        tv_capital.setText(country.getmCapital());

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
        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT);
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

}
