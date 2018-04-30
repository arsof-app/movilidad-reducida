package com.example.ibaitxo.movilidadreducida;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;

import com.example.ibaitxo.movilidadreducida.modelo.CameraPreview;

/**
 * Created by leralite on 3/8/18.
 */

public class CameraActivity extends AppCompatActivity{
    private static final int CAMERA_REQUEST = 1888;
    private static final int SHOW_INSERTACTIVITY = 3;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_camera);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && intent != null) {
            Bitmap photo = (Bitmap) intent.getExtras().get("data");
            Log.v("CameraAcitvity: ",photo.toString());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte[] byteArray = stream.toByteArray();

            Intent insertIntent = new Intent(getApplicationContext(), InsertActivity.class);
            insertIntent.putExtra("image", byteArray);
            startActivityForResult(insertIntent, SHOW_INSERTACTIVITY);
        }
        if (requestCode == CAMERA_REQUEST && resultCode == 0){
            setResult(RESULT_CANCELED);//Puede que haya que quitarlo
            finish();
        }
        if (requestCode == SHOW_INSERTACTIVITY && resultCode == Activity.RESULT_OK){
            setResult(RESULT_OK, intent);
            finish();
        }
        if (requestCode == SHOW_INSERTACTIVITY && resultCode == Activity.RESULT_CANCELED){
            setResult(RESULT_CANCELED);
            finish();
        }
    }

}
