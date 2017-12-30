package net.cwalton.mobileassignment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.FontRes;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
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
    private Switch s_fav_toggle;
    private AutoCompleteTextView actv_search;
    private ListView mListview;
    private ListAdapter mAdapter;
    private TravelDB mDb;
    private String[] mCountryNames;

    @Override
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
    public void onResume() {
        super.onResume();
        actv_search.setText("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Get db and entries
        mDb = new TravelDB(mContext);
        mCountries = mDb.getAllCountries();

        //Populate array of names for autocomplete text entry
        mCountryNames = new String[mCountries.size()];
        for (int i=0; i < mCountries.size(); i++){
            mCountryNames[i] = mCountries.get(i).getmName();
        }

        //Create view
        final View view = inflater.inflate(R.layout.fragment_country_list, container, false);

        //Setup autocomplete text edit
        actv_search = (AutoCompleteTextView) view.findViewById(R.id.actv_country_list_search);
        ArrayAdapter<String> acAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_dropdown_item_1line, mCountryNames);
        actv_search.setThreshold(1);
        actv_search.setAdapter(acAdapter);
        actv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selection = actv_search.getText().toString();
                Log.d(LOG_TAG, "Selection text = " + selection);
                actv_search.clearFocus();

                hideKeyboard(getActivity());

                mActivityComms.onCountryListItemSelected(selection);
            }
        });

        //Setup listview components and listener
        mListview = (ListView) view.findViewById(R.id.lv_country_list);
        mAdapter = new customAdapter(view.getContext());
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selection = mCountries.get(position).getmName();
                mActivityComms.onCountryListItemSelected(selection);
            }
        });

        //Setup listener for favourites switch
        s_fav_toggle = (Switch) view.findViewById(R.id.s_fav_toggle);
        s_fav_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    //Stuck here - need to update adapter list
                    mCountries.clear();
                    mCountries = mDb.getFavouriteCountries();
                    mAdapter = new customAdapter(view.getContext());
                    mListview.setAdapter(mAdapter);
                }else{
                    mCountries.clear();
                    mCountries = mDb.getAllCountries();
                    mAdapter = new customAdapter(view.getContext());
                    mListview.setAdapter(mAdapter);
                }
            }
        });

        return view;
    }

    //ref - StackOverflow - https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
    //Wouldn't hide keyboard when typing into the text field and selecting an option
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //http://www.vogella.com/tutorials/AndroidListView/article.html - for building custom list adapter
    public class customAdapter extends ArrayAdapter<Country> {
        private final Context mContext;

        public customAdapter(Context context) {
            super(context, -1, mCountries);
            this.mContext = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            View rowView = convertView;
            if (rowView == null) {
                rowView = LayoutInflater.from(getContext()).inflate(R.layout.location_list_item, parent, false);
            }

            TextView name = (TextView) rowView.findViewById(R.id.tv_location_list_name);
            ImageView favIcon = (ImageView) rowView.findViewById(R.id.iv_location_list_favicon);

            name.setText(mCountries.get(position).getmName());
            String fav = mCountries.get(position).getmFavourite();

            if (Location.LOC_FAV_FALSE.equals(fav)) {
                favIcon.setVisibility(View.INVISIBLE);
            }

            return rowView;
        }

    }

}




