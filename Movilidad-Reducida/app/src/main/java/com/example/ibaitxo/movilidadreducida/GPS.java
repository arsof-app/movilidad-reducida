package com.example.ibaitxo.movilidadreducida;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by ibai on 20/04/18.
 */

public class GPS extends Service implements  LocationListener{

    //Atributos
    private final Context context;

    //Flag para GPS
    boolean isGPSEnabled = false;

    //Flag para Network
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    //Distancia minima de cambio
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    //Tiempo minimo para actualizarese
    private static final long MIN_TIME_BW_UPDATES = 1000;

    private LocationManager locationManager;

    public GPS(Context context){
        this.context = context;
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public Location getLocation(){
        try{
            locationManager = (LocationManager)context
                    .getSystemService(LOCATION_SERVICE);

            //GPS estado
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            //Network estado
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled){
                Log.v("ERROR","No network provider enabled");
            }
            else{
                this.canGetLocation = true;
                //Primero Network provider
                if (isNetworkEnabled){
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,
                            this);
                    Log.d("Network","Network");
                    if(locationManager != null){
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                //Si esta activo el GPS
                if(isGPSEnabled){
                    if(location == null){
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                                this);
                        Log.d("GPS Enabled","GPS Enabled");
                        if(locationManager != null){
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location){

    }

    @Override
    public void onProviderDisabled(String provider){

    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    //Obtener latitud
    public double getLatitud(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    //Obtener longitud
    public double getLongitud(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("GPS is settings");

        alertDialog.setMessage("GPS is not enabled. Do you want to go to setting menu?");

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();

    }

}
