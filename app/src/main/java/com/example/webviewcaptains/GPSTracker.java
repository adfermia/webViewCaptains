package com.example.webviewcaptains;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class GPSTracker implements LocationListener {

    private final Context mContext;

    // flag for gps status
    public boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in miliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker (Context context) {
        this.mContext = context;
        getLocation();

    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            // getting gps status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Log.v("isNetworkEnabled", "=" + isNetworkEnabled);
            boolean perm = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
            if(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               // no network provider enabled

            } else {


                this.canGetLocation = true;
                // if GPS enabled get lat/long using GPS Services
                if(isGPSEnabled) {
                    location = null;
                    if(location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if(locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if( location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();


                            }
                        }
                    }
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }



        return location;
    }



    public void stopUsingGPS() {
        if(locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    // Function to get latitude

    public double getLatitude(){
        if ( location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    public double getLongitude(){
        if ( location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }
    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
