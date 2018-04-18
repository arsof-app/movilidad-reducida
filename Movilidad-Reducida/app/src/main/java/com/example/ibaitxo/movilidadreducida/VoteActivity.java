package com.example.ibaitxo.movilidadreducida;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibaitxo.movilidadreducida.modelo.GeoPoint;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

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

        TextView textViewDesc =findViewById(R.id.textViewDescripcion);
        textViewDesc.setTextSize(15);
        textViewDesc.setText(descripcion);

        TextView textViewNombre =findViewById(R.id.textViewNombre);
        textViewNombre.setTextSize(12);
        textViewNombre.setText(nombre);

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
            return object;
        }catch(ParseException e){
            Log.v("Error","Failed query");
            return null;
        }
    }

}
