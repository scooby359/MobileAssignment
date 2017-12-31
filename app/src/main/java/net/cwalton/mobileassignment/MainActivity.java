package net.cwalton.mobileassignment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by scoob on 24/12/2017.
 * Main activity, loading navigation layout - nav drawer, toolbar and fragment holder
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActivityComms {

    private static final String LOG_TAG = "MainActivity";
    private TravelDB database;
    public enum FRAGMENT_TYPE { HOME, COUNTRY_LIST, CITY_LIST, COUNTRY, CITY, SETTINGS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        database = new TravelDB(this);
        database.testDatabase();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCountryListItemSelected(String location) {
        Log.d(LOG_TAG, "Main called with ref: " + location);
        ChangeFragment(FRAGMENT_TYPE.COUNTRY, location);
    }

    @Override
    public void onCityListItemSelected(String location) {
        Log.d(LOG_TAG, "Main called with ref: " + location);
        ChangeFragment(FRAGMENT_TYPE.CITY, location);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //todo nav menu highlight not showing correctly - needs fixing
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            ChangeFragment(FRAGMENT_TYPE.HOME, null);
        } else if (id == R.id.nav_countries) {
            ChangeFragment(FRAGMENT_TYPE.COUNTRY_LIST, null);
        } else if (id == R.id.nav_cities) {
            ChangeFragment(FRAGMENT_TYPE.CITY_LIST, null);
        } else if (id == R.id.nav_settings) {
            ChangeFragment(FRAGMENT_TYPE.SETTINGS, null);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void ChangeFragment(FRAGMENT_TYPE type, String location){

        if (type == FRAGMENT_TYPE.HOME) {
            FragmentHome newFragment = new FragmentHome();
            FragmentManager fragmentManager = getFragmentManager();

            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.getBackStackEntryAt(0);
            }

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_holder, newFragment);
            transaction.commit();


        } else if (type == FRAGMENT_TYPE.COUNTRY_LIST) {
            FragmentCountryList newFragment = new FragmentCountryList();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_holder, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (type == FRAGMENT_TYPE.CITY_LIST) {
            FragmentCityList newFragment = new FragmentCityList();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_holder, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if (type == FRAGMENT_TYPE.COUNTRY) {

            FragmentCountry newFragment = new FragmentCountry();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Bundle args = new Bundle();
            args.putString(Location.FRAG_LOCATION_NAME, location);
            newFragment.setArguments(args);
            transaction.replace(R.id.fragment_holder, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if (type == FRAGMENT_TYPE.CITY) {

            FragmentCity newFragment = new FragmentCity();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Bundle args = new Bundle();
            args.putString(Location.FRAG_LOCATION_NAME, location);
            newFragment.setArguments(args);
            transaction.replace(R.id.fragment_holder, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if (type == FRAGMENT_TYPE.SETTINGS) {

            //todo

        }

    }

}

