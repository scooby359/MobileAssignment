package net.cwalton.mobileassignment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by scoob on 24/12/2017.
 * Template for Country fragment activities
 */

public class FragmentCountry extends Fragment {

    private static final String LOG_TAG = "FragmentCountry";
    private TravelDB db;
    private Country country;

    private TextView tv_name;
    private TextView tv_url;
    private TextView tv_language;
    private TextView tv_currency;
    private TextView tv_capital;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(LOG_TAG, "onAttach called");

        //Link to DB and get country instance from DB
        db = new TravelDB(context);
        country = db.getCountry("Germany");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_country, container, false);

        Log.d(LOG_TAG, "onCreateView called");

        //Link to assets in layout
        tv_name = (TextView) view.findViewById(R.id.tv_country_name);
        tv_url = (TextView) view.findViewById(R.id.tv_country_url);
        tv_language = (TextView) view.findViewById(R.id.tv_country_language);
        tv_currency = (TextView) view.findViewById(R.id.tv_country_currency);
        tv_capital = (TextView) view.findViewById(R.id.tv_country_capital);

        tv_name.setText(country.getmName());
        tv_url.setText(country.getmWikiUrl());
        tv_language.setText(country.getmLanguage());
        tv_currency.setText(country.getmCurrency());
        tv_capital.setText(country.getmCapital());

        return view;

    }

}
