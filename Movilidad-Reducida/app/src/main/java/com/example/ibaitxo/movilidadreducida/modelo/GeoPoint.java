package com.example.ibaitxo.movilidadreducida.modelo;

import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by leralite on 3/8/18.
 */

@ParseClassName("GeoPoint")
public class GeoPoint extends ParseObject{

    //Constructors
    public GeoPoint(){}


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

    public byte[] getImage(){ return getBytes("image"); }

    public String getDescription(){
        return getString("descripcion");
    }

    public String getIdObjeto() {
        return getObjectId();
    }

    public void setLongitude(double longitud){
        put("longitud",longitud);
    }

    public void setLatitude(double latitud){
        put("latitud",latitud);
    }

    public void setName(String name){
        put("nombre",name);
    }

    public void setImage(byte[] image){ put("image", image); }

    public void setDescription(String description){
        put("descripcion",description);
    }

}
