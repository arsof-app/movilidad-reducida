package com.example.ibaitxo.movilidadreducida;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.example.ibaitxo.movilidadreducida.modelo.CameraPreview;

/**
 * Created by leralite on 3/8/18.
 */

public class CameraActivity extends AppCompatActivity{
    private Bitmap image;
    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Camera cam = getCameraInstance();

        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK); // attempt to get a Camera instance
        }
        catch (Exception e) {
            Log.v("Camera error: " + e.getMessage(), "getCameraInstance");
        }
        return c; // returns null if camera is unavailable
    }
}
