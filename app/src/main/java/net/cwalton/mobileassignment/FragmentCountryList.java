package net.cwalton.mobileassignment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by scoob on 15/12/2017.
 * Country list display - may merge with cities later on
 */

public class FragmentCountryList extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_list, container, false);
    }
}




