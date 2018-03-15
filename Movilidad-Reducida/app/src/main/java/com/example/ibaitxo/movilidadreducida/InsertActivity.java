package com.example.ibaitxo.movilidadreducida;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by leralite on 3/15/18.
 */

public class InsertActivity extends AppCompatActivity{
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        byte[] byteArray = intent.getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setImageBitmap(image);

    }

}
