package net.cwalton.mobileassignment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by scoob on 24/12/2017.
 * Main activity, loading navigation layout - nav drawer, toolbar and fragment holder
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActivityComms {

    public enum FRAGMENT_TYPE { HOME, COUNTRY_LIST, CITY_LIST, COUNTRY, CITY, SETTINGS}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set preferences if first run
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        setContentView(R.layout.activity_main);

        //If first launch and frag holder is null, set home fragment
        if (findViewById(R.id.fragment_holder) != null){
            if (savedInstanceState != null){
                return;
            }
            FragmentHome newFragment = new FragmentHome();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_holder, newFragment)
                    .commit();
        }

        //Setup tooldbar and nav drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    //Manage fragments - handle interface calls and change fragment to selected type
    @Override
    public void onCountryListItemSelected(String location) {
        ChangeFragment(FRAGMENT_TYPE.COUNTRY, location);
    }

    @Override
    public void onCityListItemSelected(String location) {
        ChangeFragment(FRAGMENT_TYPE.CITY, location);
    }

    @Override
    public void openCityList() {
        ChangeFragment(FRAGMENT_TYPE.CITY_LIST, null);
    }

    @Override
    public void openCountryList() {
        ChangeFragment(FRAGMENT_TYPE.COUNTRY_LIST, null);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            ChangeFragment(FRAGMENT_TYPE.HOME, null);
        } else if (id == R.id.nav_countries) {
            ChangeFragment(FRAGMENT_TYPE.COUNTRY_LIST, null);
        } else if (id == R.id.nav_cities) {
            ChangeFragment(FRAGMENT_TYPE.CITY_LIST, null);
        }else if (id == R.id.nav_settings){
            ChangeFragment(FRAGMENT_TYPE.SETTINGS, null);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ChangeFragment(FRAGMENT_TYPE type, String location){

        Bundle args = new Bundle();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (type != FRAGMENT_TYPE.HOME) {
            transaction.addToBackStack(null);
        }

        if (type == FRAGMENT_TYPE.HOME) {

            //Clear backstack queue
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            FragmentHome newFragment = new FragmentHome();
            transaction.replace(R.id.fragment_holder, newFragment);

        } else if (type == FRAGMENT_TYPE.COUNTRY_LIST) {

            FragmentCountryList newFragment = new FragmentCountryList();
            transaction.replace(R.id.fragment_holder, newFragment);

        } else if (type == FRAGMENT_TYPE.CITY_LIST) {

            FragmentCityList newFragment = new FragmentCityList();
            transaction.replace(R.id.fragment_holder, newFragment);

        }else if (type == FRAGMENT_TYPE.COUNTRY) {

            FragmentCountry newFragment = new FragmentCountry();
            args.putString(Location.FRAG_LOCATION_NAME, location);
            newFragment.setArguments(args);
            transaction.replace(R.id.fragment_holder, newFragment);

        }else if (type == FRAGMENT_TYPE.CITY) {
            FragmentCity newFragment = new FragmentCity();
            args.putString(Location.FRAG_LOCATION_NAME, location);
            newFragment.setArguments(args);
            transaction.replace(R.id.fragment_holder, newFragment);

        }else if (type == FRAGMENT_TYPE.SETTINGS) {
            FragmentSettings newFragment = new FragmentSettings();
            transaction.replace(R.id.fragment_holder, newFragment);
        }

        transaction.commit();
    }

}

