package com.example.ibaitxo.movilidadreducida;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ibaitxo.movilidadreducida.modelo.GeoPoint;

public class VoteActivity extends AppCompatActivity {

    GeoPoint gP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        gP = getObject(bundle);
        String descripcion = gP.getDescription();
        String nombre = gP.getName();
        byte[] byteImage = gP.getImage();

        TextView textViewDesc =findViewById(R.id.textViewDescripcion);
        textViewDesc.setTextSize(15);
        textViewDesc.setText(descripcion);

        TextView textViewNombre =findViewById(R.id.textViewNombre);
        textViewNombre.setTextSize(12);
        textViewNombre.setText(nombre);

        Bitmap image = BitmapFactory.decodeByteArray(byteImage,0,byteImage.length);
        ImageView imViewImage = findViewById(R.id.imageView);
        imViewImage.setImageBitmap(image);

        Button volver = (Button)findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });


    }

    private GeoPoint getObject(Bundle bundle){
        String name = bundle.getString("nombre");
        ParseQuery<GeoPoint> query = ParseQuery.getQuery("GeoPoint");
        query.whereEqualTo("nombre",name);
        try{
            GeoPoint object = query.getFirst();
            object.getDescription();
            object.getImage();
            return object;
        }catch(ParseException e){
            Log.v("Error","Failed query");
            return null;
        }
    }

}
