package net.cwalton.mobileassignment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by scoob on 24/12/2017.
 * Main activity, loading navigation layout - nav drawer, toolbar and fragment holder
 */

//General todos:
    //Todo - Back on homescreen should clear queue - instead, if on city / country, jumps back to list
    //Todo - Put labels on country / city page buttons
    //Todo - Fix toast on fave selection in city/country page
    //Todo - Refactor city/country code to reduce repetition
    //Todo - allow list view to set fave
    //Todo - County pages - link cities to city pages
    //Todo - Put Wiki pages into webview to retain in app
    //Todo - settings for temp control

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActivityComms {

    private static final String LOG_TAG = "MainActivity";
    private TravelDB database;
    public enum FRAGMENT_TYPE { HOME, COUNTRY_LIST, CITY_LIST, COUNTRY, CITY, SETTINGS}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //If first launch and frag holder is null..
        if (findViewById(R.id.fragment_holder) != null){
            if (savedInstanceState != null){
                return;
            }

            FragmentHome newFragment = new FragmentHome();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_holder, newFragment)
                    .commit();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        database = new TravelDB(this);
        database.testDatabase();
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ChangeFragment(FRAGMENT_TYPE type, String location){

        if (type == FRAGMENT_TYPE.HOME) {
            //Todo - should call original fragment off stack instead of making new (stop multiple weather requests)
            FragmentHome newFragment = new FragmentHome();
            FragmentManager fragmentManager = getFragmentManager();

            //Clear backstack queue
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            Bundle args = new Bundle();
            newFragment.setArguments(args);


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

