package com.example.ibaitxo.movilidadreducida;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.ibaitxo.movilidadreducida.modelo.GeoPoint;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MapsActivity extends AppCompatActivity {

    private MapView mapView = null;
    private MapboxMap mapboxMap = null;
    TravelPointsApplication tpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapboxAccountManager.start(this, getString(R.string.access_token));
        setContentView(R.layout.activity_maps);
        tpa = (TravelPointsApplication) getApplicationContext();

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap map) {
                mapboxMap = map;
                getServerList(mapboxMap);
                Button updateMarkers = (Button)findViewById(R.id.updateMarkers);
                updateMarkers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getServerList(mapboxMap);
                    }
                });


            }
        });
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void getServerList(final MapboxMap mapboxMap) {

        ParseQuery<GeoPoint> query = ParseQuery.getQuery("GeoPoint");
        query.findInBackground(new FindCallback<GeoPoint>() {
            public void done(List<GeoPoint> objects, ParseException e) {
                if (e == null) {
                    tpa.pointList = objects;
                    mapboxMap.addImage(
                            "my-marker-image",
                            BitmapFactory.decodeResource(MapsActivity.this.getResources(),
                                    R.drawable.default_marker)
                    );
                    for(GeoPoint gp : tpa.pointList){
                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(gp.getLatitude(),gp.getLongitude()))
                                .title(gp.getName())
                        );
                    }
                    Log.v("query OK ", "getServerList()");
                } else {
                    Log.v("error query, reason: " + e.getMessage(), "getServerList()");
                    Toast.makeText(
                            getBaseContext(),
                            "getServerList(): error  query, reason: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
