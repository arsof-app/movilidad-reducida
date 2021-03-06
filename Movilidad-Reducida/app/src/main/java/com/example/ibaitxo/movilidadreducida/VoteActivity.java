package com.example.ibaitxo.movilidadreducida;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibaitxo.movilidadreducida.modelo.GeoPoint;
import com.example.ibaitxo.movilidadreducida.modelo.Voto;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VoteActivity extends AppCompatActivity {

    GeoPoint gP;
    Voto vote;
    String idObjeto;
    List<String> macList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vote);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gP = getObject(bundle);
        idObjeto = gP.getIdObjeto();

        String descripcion = gP.getDescription();
        String nombre = gP.getName();
        byte[] byteArray = gP.getImage();

        TextView textViewDesc =findViewById(R.id.textViewDescripcion);
        textViewDesc.setTextSize(15);
        textViewDesc.setText(descripcion);

        TextView textViewNombre =findViewById(R.id.textViewNombre);
        textViewNombre.setTextSize(12);
        textViewNombre.setText(nombre);


        Bitmap image = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        ImageView imViewImage = findViewById(R.id.imageView);
        imViewImage.setImageBitmap(image);

        Button volver = findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button votar = findViewById(R.id.votar);
        votar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtenemos la mac
                String address = getMacAddr();

                //Buscamos el objeto Voto por IdZona obtenida de idObjeto de GeoPoint
                vote = getVote(idObjeto);
                List<String> macList = vote.getListaMac();
                int votos = vote.getVotos();
                String idZona = vote.getIdZona();
                if(!macList.contains(address)){
                    macList.add(address);
                    updateVotos(macList,++votos,idZona);
                    Toast.makeText(getApplicationContext(),"Votacion realizada correctamente",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Ya se ha votado anteriormente",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private GeoPoint getObject(Bundle bundle){
        String name = bundle.getString("nombre");
        double lat = bundle.getDouble("latitud");
        double lon = bundle.getDouble("longitud");
        ParseQuery<GeoPoint> queryName = ParseQuery.getQuery("GeoPoint");
        ParseQuery<GeoPoint> queryLat = ParseQuery.getQuery("GeoPoint");
        ParseQuery<GeoPoint> queryLon = ParseQuery.getQuery("GeoPoint");
        queryName.whereEqualTo("nombre",name);
        queryLat.whereEqualTo("latitud",lat);
        queryLon.whereEqualTo("longitud",lon);
        List<ParseQuery<GeoPoint>> queries = new ArrayList<ParseQuery<GeoPoint>>();
        queries.add(queryName);
        queries.add(queryLat);
        queries.add(queryLon);
        ParseQuery<GeoPoint> mainQuery = ParseQuery.or(queries);
        try{
            GeoPoint object = mainQuery.getFirst();
            object.getIdObjeto();
            object.getDescription();
            object.getImage();
            object.getName();
            return object;
        }catch(ParseException e){
            Log.v("Error","Failed query");
            return null;
        }
    }

    private Voto getVote(String idObjeto){
        ParseQuery<Voto> query = ParseQuery.getQuery("Voto");
        query.whereEqualTo("idZona",idObjeto);
        try{
            Voto object = query.getFirst();
            object.getListaMac();
            object.getVotos();
            object.getIdObjeto();
            return object;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateVotos(List<String> macList, int votos, String idZona){
        ParseQuery<Voto> query = ParseQuery.getQuery("Voto");
        query.whereEqualTo("idZona",idZona);
        try{
            Voto object = query.getFirst();
            object.setListMac(macList);
            object.setVotos(votos);
            object.saveInBackground();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
