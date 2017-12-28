package net.cwalton.mobileassignment;

import android.app.Fragment;
import android.content.Context;
import android.media.Image;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;
import java.util.logging.ConsoleHandler;

/**
 * Created by scoob on 15/12/2017.
 * Country list display - may merge with cities later on
 */

public class FragmentCountryList extends Fragment {

    private static final String LOG_TAG = "FragmentCountryList";
    private Context mContext;
    private TravelDB mDb;
    private List<Country> mCountries;
    private ListView mListview;

    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        Log.d(LOG_TAG, "onAttach called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDb = new TravelDB(mContext);
        mCountries = mDb.getAllCountries();

        View view = inflater.inflate(R.layout.fragment_country_list, container, false);
        mListview = (ListView) view.findViewById(R.id.lv_country_list);
        ListAdapter adapter = new customAdapter(view.getContext());
        mListview.setAdapter(adapter);

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
                rowView = getLayoutInflater().inflate(R.layout.location_list_item, parent, false);
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




