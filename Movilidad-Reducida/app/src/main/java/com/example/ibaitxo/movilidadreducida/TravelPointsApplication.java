package com.example.ibaitxo.movilidadreducida;

import android.app.Application;

import com.example.ibaitxo.movilidadreducida.modelo.GeoPoint;
import com.example.ibaitxo.movilidadreducida.modelo.Voto;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class TravelPointsApplication extends Application {


    public List<GeoPoint> pointList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(GeoPoint.class);
        ParseObject.registerSubclass(Voto.class);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("myAppId") //si no has cambiado APP_ID, sino pon el valor de APP_ID
                .clientKey("empty")
                .server("https://movilidad-reducida.herokuapp.com/parse/")
                .build());
    }
}
