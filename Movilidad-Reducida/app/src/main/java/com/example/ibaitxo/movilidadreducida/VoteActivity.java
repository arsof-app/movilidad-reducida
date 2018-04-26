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

import java.util.ArrayList;
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
                Intent intent =  new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        Button votar = findViewById(R.id.votar);
        votar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtenemos la mac
                WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = manager.getConnectionInfo();
                String address = info.getMacAddress();


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


}
