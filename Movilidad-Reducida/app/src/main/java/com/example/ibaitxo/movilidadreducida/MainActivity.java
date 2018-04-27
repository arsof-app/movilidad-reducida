package com.example.ibaitxo.movilidadreducida;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ibaitxo.movilidadreducida.modelo.GeoPoint;
import com.example.ibaitxo.movilidadreducida.modelo.Voto;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int SHOW_CAMERAACTIVITY = 2;
    ListView list;
    ArrayAdapter<GeoPoint> todoItemsAdapter;
    TravelPointsApplication tpa;
    GeoPoint geoPoint;
    GPS gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intentMaps = new Intent(getApplicationContext(), MapsActivity.class);
        gps = new GPS(getApplicationContext());
        Button verMapa = findViewById(R.id.verMapa);
        Button anadirZona = findViewById(R.id.anadirZona);
        Button salir = findViewById(R.id.salir);

        verMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentMaps);
            }
        });

        anadirZona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivityForResult(cameraIntent, SHOW_CAMERAACTIVITY);
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salirApp(view);
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SHOW_CAMERAACTIVITY && resultCode == RESULT_OK)
            {
                Bundle bundle = data.getExtras();
                Double latitud = bundle.getDouble("latitud");
                Double longitud = bundle.getDouble("longitud");
                byte[] byteArray = bundle.getByteArray("image");

                String nombre = bundle.getString("nombre");
                String desc = bundle.getString("text");
                newParseObject(nombre,desc,latitud,longitud, byteArray);

                //Necesitamos el id de la Zona
                String idObjeto = getIdZona(nombre,latitud,longitud);

                List<String> macList = new ArrayList<>();
                //Obtenemos la mac
                WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = manager.getConnectionInfo();
                String address = info.getMacAddress();
                macList.add(address);

                //Hacemos la primera insercion de voto para el usuario que inserta en mapa
                newParseVoteObject(1,macList,idObjeto);

            }
        }
    }


    private void newParseObject(String nombre,String desc, Double latitud, Double longitud, byte[] image) {
        geoPoint = new GeoPoint();
        geoPoint.setName(nombre);
        geoPoint.setDescription(desc);
        geoPoint.setLongitude(longitud);
        geoPoint.setLatitude(latitud);
        geoPoint.setImage(image);



        geoPoint.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //tpa.pointList.add(geoPoint);
                    //todoItemsAdapter.notifyDataSetChanged();
                    Log.v("object updated:", "updateParseObject()");
                } else {
                    Log.v("save failed, reason: "+ e.getMessage(), "newParseObject()");
                    Toast.makeText(
                            getBaseContext(),
                            "newParseObject(): Object save failed  to server, reason: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void newParseVoteObject(int voto, List mac, String idZona) {
        ParseObject votoObj = new ParseObject("Voto");
        votoObj.put("idZona",idZona);
        votoObj.put("macList",mac);
        votoObj.put("votos",voto);

        votoObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.v("object updated:", "updateParseObject()");
                } else {
                    Log.v("save failed, reason: "+ e.getMessage(), "newParseObject()");
                    Toast.makeText(
                            getBaseContext(),
                            "newParseObject(): Object save failed  to server, reason: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private String getIdZona(String nombre, double latitud, double longitud){
        ParseQuery<GeoPoint> queryName = ParseQuery.getQuery("GeoPoint");
        ParseQuery<GeoPoint> queryLat = ParseQuery.getQuery("GeoPoint");
        ParseQuery<GeoPoint> queryLon = ParseQuery.getQuery("GeoPoint");
        queryName.whereEqualTo("nombre",nombre);
        queryLat.whereEqualTo("latitud",latitud);
        queryLon.whereEqualTo("longitud",longitud);
        List<ParseQuery<GeoPoint>> queries = new ArrayList<ParseQuery<GeoPoint>>();
        queries.add(queryName);
        queries.add(queryLat);
        queries.add(queryLon);
        ParseQuery<GeoPoint> mainQuery = ParseQuery.or(queries);
        try{
            GeoPoint object = mainQuery.getFirst();
            return object.getIdObjeto();
        }catch(ParseException e){
            Log.v("Error","Failed query");
            return null;
        }
    }

    public void salirApp (View view){ finishAffinity(); }
}
