package com.example.ibaitxo.movilidadreducida.modelo;

import android.graphics.Bitmap;

/**
 * Created by leralite on 3/8/18.
 */

public class GeoPoint {
    private Double longitude;
    private Double latitude;
    private Bitmap image;
    private String name;
    private String description;

    //Constructors
    public GeoPoint(){}

    public GeoPoint(Double longitude, Double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public GeoPoint(String name, Double longitude, Double latitude){
        this(longitude, latitude);
        this.name = name;
    }

    //Methods
    public Double getLongitude(){
        return longitude;
    }

    public Double getLatitude(){
        return latitude;
    }

    public String getName(){
        return name;
    }

    public Bitmap getImage(){
        return image;
    }

    public String getDescription(){
        return description;
    }

    public void setLongitude(){
        this.longitude = longitude;
    }

    public void setLatitude(){
        this.latitude = latitude;
    }

    public void setName(){
        this.name = name;
    }

    public void setImage(Bitmap image){
        this.image = image;
    }

    public void setDescription(Double longitude){
        this.longitude = longitude;
    }

}
