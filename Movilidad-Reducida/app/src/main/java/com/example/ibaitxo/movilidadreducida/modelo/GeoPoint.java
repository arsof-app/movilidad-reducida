package com.example.ibaitxo.movilidadreducida.modelo;

import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by leralite on 3/8/18.
 */

@ParseClassName("GeoPoint")
public class GeoPoint extends ParseObject{
    private double longitud;
    private double latitud;
    private Bitmap image;
    private String name;
    private String description;

    //Constructors
    public GeoPoint(){}

    public GeoPoint(double longitud, double latitud){
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public GeoPoint(String name, double longitud, double latitud){
        this.longitud = longitud;
        this.latitud = latitud;
        this.name = name;
    }

    //Methods
    public double getLongitude(){
        return getDouble("longitud");
    }

    public double getLatitude(){
        return getDouble("latitud");
    }

    public String getName(){
        return  getString("nombre");
    }

    public Bitmap getImage(){
        return image;
    }

    public String getDescription(){
        return description;
    }

    public void setLongitude(double longitud){
        put("longitud",longitud);
    }

    public void setLatitude(double latitud){
        put("latitud",latitud);
    }

    public void setName(){
        put("nombre",name);
    }

    public void setImage(Bitmap image){
        this.image = image;
    }

    public void setDescription(String description){
        put("descripcion",description);
    }

}
