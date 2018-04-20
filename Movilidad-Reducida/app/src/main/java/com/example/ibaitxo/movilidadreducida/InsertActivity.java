package com.example.ibaitxo.movilidadreducida;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ibaitxo.movilidadreducida.modelo.GeoPoint;
import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * Created by leralite on 3/15/18.
 */

public class InsertActivity extends AppCompatActivity{
    private byte[] byteArray;
    private ImageView imageView;
    private EditText desc;
    private EditText nombre;
    private Bitmap image;
    GPS gps;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        gps = new GPS(getApplicationContext());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        byteArray = intent.getByteArrayExtra("image");
        image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView = findViewById(R.id.image_view);
        imageView.setImageBitmap(image);

        desc = findViewById(R.id.text);
        nombre = findViewById(R.id.nombre);
        Button subir = findViewById(R.id.button_subir);
        Button cancelar = findViewById(R.id.button_cancelar);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double longitude =  gps.getLocation().getLongitude();
                double latitude = gps.getLocation().getLatitude();


                Bundle bundle = new Bundle();
                bundle.putByteArray("image", byteArray);
                bundle.putDouble("longitud", longitude);
                bundle.putDouble("latitud", latitude);
                bundle.putString("text", desc.getText().toString());
                bundle.putString("nombre", nombre.getText().toString());
                Intent intent =  new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    Location updateLocation(){

        Location location = gps.getLocation();
        return location;
    }
}
