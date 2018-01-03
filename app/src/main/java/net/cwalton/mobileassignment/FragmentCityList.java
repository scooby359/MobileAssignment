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

    private Context context;

    private List<City> cities;
    private ActivityComms activityComms;
    private TravelDB db;

    private EditText etSearch;
    private Switch sFavToggle;
    private ListView listView;
    private ListAdapter adapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        activityComms = (ActivityComms)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityComms = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        etSearch.setText("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Setup view and get location information
        final View view = inflater.inflate(R.layout.fragment_city_list, container, false);
        db = new TravelDB(context);
        cities = db.getAllCities();

        //Setup search box
        etSearch = view.findViewById(R.id.et_city_list_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(LOG_TAG, "Text change = " + charSequence);
                filter(charSequence.toString(), view, sFavToggle.isChecked());
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
        listView = view.findViewById(R.id.lv_city_list);
        adapter = new customAdapter(view.getContext());
        listView.setAdapter(adapter);

        //Setup listener for favourites switch
        sFavToggle = view.findViewById(R.id.s_fav_toggle);
        sFavToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                cities.clear();
                cities = db.filterCities(etSearch.getText().toString(), sFavToggle.isChecked());
                adapter = new customAdapter(view.getContext());
                listView.setAdapter(adapter);
            }
        });
        return view;
    }

    //Get filtered search results
    private void filter(String input, View view, Boolean favouritesOnly){
        cities.clear();
        cities = db.filterCities(input, favouritesOnly);
        adapter = new customAdapter(view.getContext());
        listView.setAdapter(adapter);
    }

    //http://www.vogella.com/tutorials/AndroidListView/article.html
    // Setup custom list adapter
    public class customAdapter extends ArrayAdapter<City> {

        public customAdapter(Context context) {
            super(context, -1, cities);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            View rowView = convertView;
            if (rowView == null) {
                rowView = LayoutInflater.from(getContext()).inflate(R.layout.location_list_item, parent, false);
            }

            final TextView name = rowView.findViewById(R.id.tv_location_list_name);
            final ImageView favIcon = rowView.findViewById(R.id.iv_location_list_favicon);

            name.setText(cities.get(position).getName());
            String fav = cities.get(position).getFavourite();

            if (Location.LOC_FAV_FALSE.equals(fav)) {
                favIcon.setColorFilter(getResources().getColor(R.color.fav_gray));
            }else{
                favIcon.setColorFilter(getResources().getColor(R.color.fav_yellow));
            }

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activityComms.onCityListItemSelected(name.getText().toString());
                }
            });

            favIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String response = db.toggleCityFavourite(name.getText().toString());
                    if (response.equals(Location.LOC_FAV_TRUE)){
                        favIcon.setColorFilter(getResources().getColor(R.color.fav_yellow));
                    }else {
                        favIcon.setColorFilter(getResources().getColor(R.color.fav_gray));
                    }
                }
            });

            return rowView;
        }
    }

    //https://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
    //Keyboard was staying up if typing then clicked on screen - had to copy code from online as no clear 'official' way around this
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}




