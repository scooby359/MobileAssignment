package net.cwalton.mobileassignment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.zip.Inflater;

public class SplashScreen extends AppCompatActivity {

    private int mHaveLocPermission;
    private static final int PERMISSIONS_LOCATION_ID = 1;
    private static final String LOG_TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Location bits
        mHaveLocPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        Log.d(LOG_TAG, "Initial permission val = " + mHaveLocPermission);


        //If not, request
        if (mHaveLocPermission != PackageManager.PERMISSION_GRANTED){
            //if we don't have permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_LOCATION_ID);
        }else{
            startApp();
        }

        Log.d(LOG_TAG, "After request method permission val = " + mHaveLocPermission);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d(LOG_TAG, "onResult permission val = " + mHaveLocPermission);

        switch (requestCode){
            case PERMISSIONS_LOCATION_ID: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission granted
                    mHaveLocPermission = PackageManager.PERMISSION_GRANTED;
                }else {
                    mHaveLocPermission = PackageManager.PERMISSION_DENIED;
                }
            }
        }

        startApp();
    }

    public void startApp(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
