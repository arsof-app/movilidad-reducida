package com.example.ibaitxo.movilidadreducida;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int SHOW_CAMERAACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intentMaps = new Intent(getApplicationContext(), MapsActivity.class);

        Button verMapa = (Button)findViewById(R.id.verMapa);
        Button anadirZona = (Button)findViewById(R.id.anadirZona);
        Button salir = (Button)findViewById(R.id.salir);

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


    public void salirApp (View view){
        finish();
    }
}
