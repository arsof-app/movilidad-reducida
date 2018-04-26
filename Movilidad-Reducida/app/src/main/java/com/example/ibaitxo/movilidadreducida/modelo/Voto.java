package com.example.ibaitxo.movilidadreducida.modelo;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibai on 26/04/18.
 */

@ParseClassName("Voto")
public class Voto extends ParseObject {

    public Voto(){}

    //Methods
    public int getVotos(){
        return getInt("votos");
    }

    public List<String> getListaMac(){
        return getList("macList");
    }

    public String getIdObjeto(){
        return  getString("_id");
    }

    public String getIdZona(){
        return getString("idZona");
    }

    public void setVotos(int votos){
        put("votos",votos++);
    }

    public void setListMac(List macList){
        put("macList",macList);
    }

    public void setIdZona(String idZona) { put("idZona",idZona);}
}
