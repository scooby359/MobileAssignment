package net.cwalton.mobileassignment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by scoob on 15/12/2017.
 * Country list display - may merge with cities later on
 */

//Horton, J., 2015. Android Programming for Beginners. Birmingham: Packt Publishing.
//pg 444 - fragment communications

public class FragmentCountryList extends Fragment {

    private static final String LOG_TAG = "FragmentCountryList";
    private Context mContext;
    private List<Country> mCountries;
    private ActivityComms mActivityComms;

    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivityComms = (ActivityComms)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityComms = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TravelDB mDb = new TravelDB(mContext);
        mCountries = mDb.getAllCountries();

        View view = inflater.inflate(R.layout.fragment_country_list, container, false);
        ListView mListview = (ListView) view.findViewById(R.id.lv_country_list);
        ListAdapter adapter = new customAdapter(view.getContext());
        mListview.setAdapter(adapter);

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selection = mCountries.get(position).getmName();
                mActivityComms.onCountryListItemSelected(selection);
            }
        });

        return view;
    }


    //http://www.vogella.com/tutorials/AndroidListView/article.html - for building custom list adapter
    public class customAdapter extends ArrayAdapter<Country>{
        private final Context mContext;

        customAdapter(Context context){
            super(context, -1, mCountries);
            this.mContext = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            View rowView = convertView;
            if (rowView == null) {
                rowView = LayoutInflater.from(getContext()).inflate(R.layout.location_list_item, parent, false);
                        //getLayoutInflater().inflate(R.layout.location_list_item, parent, false);
            }

            TextView name = (TextView) rowView.findViewById(R.id.tv_location_list_name);
            ImageView favIcon = (ImageView) rowView.findViewById(R.id.iv_location_list_favicon);

            name.setText(mCountries.get(position).getmName());
            String fav = mCountries.get(position).getmFavourite();

            if (Location.LOC_FAV_FALSE.equals(fav) || fav == null){
                favIcon.setVisibility(View.INVISIBLE);
            }

            return rowView;
        }


    }

}




