package net.cwalton.mobileassignment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by scoob on 15/12/2017.
 * City list display - may merge with cities later on
 */

//Horton, J., 2015. Android Programming for Beginners. Birmingham: Packt Publishing.
//pg 444 - fragment communications

public class FragmentCityList extends Fragment {

    private static final String LOG_TAG = "FragmentCityList";

    private Context mContext;

    private List<City> mCities;
    private ActivityComms mActivityComms;
    private TravelDB mDb;

    private Switch sFavToggle;
    private EditText etSearch;
    private ListView mListview;
    private ListAdapter mAdapter;


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
        etSearch.setText("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Get db and entries
        mDb = new TravelDB(mContext);
        mCities = mDb.getAllCities();

        //Create view
        final View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        //Setup search box
        etSearch = (EditText) view.findViewById(R.id.et_city_list_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(LOG_TAG, "Text change = " + charSequence);
                filter(charSequence.toString(), view);

                //todo - if fav switch on, deleting all characters shows all entries in error
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        //Setup listview components and listener
        mListview = (ListView) view.findViewById(R.id.lv_city_list);
        mAdapter = new customAdapter(view.getContext());
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selection = mCities.get(position).getmName();
                mActivityComms.onCityListItemSelected(selection);
            }
        });

        //Setup listener for favourites switch
        sFavToggle = (Switch) view.findViewById(R.id.s_fav_toggle);
        sFavToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                etSearch.setText("");
                if (isChecked){
                    //Stuck here - need to update adapter list
                    mCities.clear();
                    mCities = mDb.getFavouriteCities();
                    mAdapter = new customAdapter(view.getContext());
                    mListview.setAdapter(mAdapter);
                }else{
                    mCities.clear();
                    mCities = mDb.getAllCities();
                    mAdapter = new customAdapter(view.getContext());
                    mListview.setAdapter(mAdapter);
                }
            }
        });

        return view;
    }

    //Get filtered search results
    public void filter(String input, View view){
        input = input.toLowerCase();
        mCities.clear();
        mCities = mDb.filterCities(input);
        mAdapter = new customAdapter(view.getContext());
        mListview.setAdapter(mAdapter);
    }

    //http://www.vogella.com/tutorials/AndroidListView/article.html - for building custom list adapter
    public class customAdapter extends ArrayAdapter<City> {

        public customAdapter(Context context) {
            super(context, -1, mCities);
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

            name.setText(mCities.get(position).getmName());
            String fav = mCities.get(position).getmFavourite();

            if (Location.LOC_FAV_FALSE.equals(fav)) {
                favIcon.setVisibility(View.INVISIBLE);
            }

            return rowView;
        }
    }

    //https://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
    //Keyboard was staying up if typing then clicked on screen - had to copy code from online as no clear 'official' way around this
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}




