package com.example.ibaitxo.movilidadreducida;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ibaitxo.movilidadreducida.modelo.GeoPoint;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    private static final int SHOW_CAMERAACTIVITY = 2;
    ListView list;
    ArrayAdapter<GeoPoint> todoItemsAdapter;
    TravelPointsApplication tpa;
    GeoPoint geoPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intentMaps = new Intent(getApplicationContext(), MapsActivity.class);

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
                byte[] image = bundle.getByteArray("image");
                String desc = bundle.getString("text");


                newParseObject(desc,latitud,longitud, image);

            }
        }
    }

    private void newParseObject(String desc, Double latitud, Double longitud, byte[] image) {
        geoPoint = new GeoPoint();
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


    public void salirApp (View view){ finishAffinity(); }
}
