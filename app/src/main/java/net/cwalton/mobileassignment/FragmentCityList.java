package net.cwalton.mobileassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by scoob on 24/12/2017.
 * Template for CityList, to display all cities on page.. may be merged with country list later for simplicity
 */

public class FragmentCityList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_city_list);
    }
}
