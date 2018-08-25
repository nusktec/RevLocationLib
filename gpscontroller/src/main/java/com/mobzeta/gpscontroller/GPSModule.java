package com.mobzeta.gpscontroller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class GPSModule extends Activity {
    private Context context;
    private LocationManager locationManager;
    private Location location;
    double _longitude;
    double _latitude;

    public GPSModule(final Context context) {
        this.context = context;
        init(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Display Onchanged
                Toast.makeText(context, location.getProvider(),Toast.LENGTH_LONG).show();
                Log.e("Longi", String.valueOf(location.getLongitude()));
                Log.e("Lati", String.valueOf(location.getLatitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    /**
     * Get longitude and Latitude to query online
     */
    public void init(LocationListener locationListener) {
        this.locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if(ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                Log.e("RevLocationLib","Allow the the permission...");
            }
            Log.e("RevLocationLib","Permission was not granted to access location...");
            return;
        }
        locationManager.requestLocationUpdates(locationManager.getBestProvider(highCriteria(), true), 2000, 0.0f, locationListener, getMainLooper());
    }


    /**
     * Define your location criteria
     */
    //this method for high accuracy
    private static Criteria highCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setBearingRequired(false);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        return criteria;
    }

    private static Criteria LowCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setBearingRequired(false);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        return criteria;
    }

}
