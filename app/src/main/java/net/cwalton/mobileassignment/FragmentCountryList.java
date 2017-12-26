package net.cwalton.mobileassignment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

/**
 * Created by scoob on 15/12/2017.
 * Country list display - may merge with cities later on
 */

public class FragmentCountryList extends Fragment {

    private static final String LOG_TAG = "FragmentCountryList";
    private TravelDB mDb;
    private List<Country> mCountries;

    private ListView mListview;

    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(LOG_TAG, "onAttach called");

        //Link to DB and get country instance from DB
        mDb = new TravelDB(context);
        mCountries = mDb.getAllCountries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_country, container, false);

        Log.d(LOG_TAG, "onCreateView called");

        //Link to assets in layout
        //tv_name = (TextView) view.findViewById(R.id.tv_country_name);
        //tv_name.setText(country.getmName());

        mListview = (ListView) view.findViewById(R.id.lv_country_list);
        String[] countryNames = new String[mCountries.size()];


        return view;
    }
}




