package com.example.habittracker2017;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by 82520 on 2017-11-22.
 */

public class MainModel {
    // INCOMPLETE MODEL ONLY WORKS FOR LOCATION NOW
    static private Location EventLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;

    MainModel(){
    }
    /**
     * activate gps and look for current location
     * @param context context of activity
     */
    public void startLocationListen(Context context){
        // referenced https://developer.android.com/guide/topics/location/strategies.html
        // March 12, 2017 10:00pm

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                EventLocation = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        catch (SecurityException e){
            Log.d("location", e.toString());
        }
    }

    /**
     * stop gps from looking for current location
     */
    public void stopLocationListener(){
        try {
            EventLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.removeUpdates(locationListener);
        }
        catch(SecurityException e){
            Log.d("Location error", e.toString());

        }
    }

    /**
     * return current location
     * @return current location
     */
    public Location getLocation(){
        return EventLocation;
    }
}
