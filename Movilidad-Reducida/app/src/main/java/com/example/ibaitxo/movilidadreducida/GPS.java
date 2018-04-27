package com.example.ibaitxo.movilidadreducida;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by ibai on 20/04/18.
 */

public class GPS {
    Context _context;
    Location _location;
    LocationManager _locationManager;
    String _provider = LocationManager.GPS_PROVIDER;

    GPS(Context context) {
        _context = context;
        _locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        //Permisos
        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            return;
        }

        _locationManager.requestLocationUpdates(_provider, 1000, 1, new LocationListener() {
            public void onLocationChanged(Location location) {
                _location = location;
            }

            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            public void onProviderEnabled(String s) {
            }

            public void onProviderDisabled(String s) {
            }
        });
    }

    Location getLocation() {
        if (_location == null) {
                _location = this.getLastKnownLocation();
        }

        return _location;
    }

    public Location getLastKnownLocation() {
        LocationManager _locationManager = (LocationManager) _context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = _locationManager.getProviders(true);
        Location bestLocation = null;

        if (providers == null || providers.size() == 0) return null;

        try {
            for (String provider : providers) {
                if (provider.equals(LocationManager.PASSIVE_PROVIDER)) continue;
                Location l = _locationManager.getLastKnownLocation(provider);
                if (l == null) continue;
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) bestLocation = l;
            }
        } catch (SecurityException ignored) {}



        return bestLocation;
    }


}
