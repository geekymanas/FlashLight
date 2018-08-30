package com.example.gopi.flashlight;


import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static CameraManager mCameraManager;
    static SensorManager sensorManager;
    static Sensor accelerometer;
    private int count = 0;
    static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button on = findViewById(R.id.torch);
        Button off = findViewById(R.id.google);
        textView = findViewById(R.id.textView);

        mCameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        //sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        final Intent intent = new Intent(MainActivity.this,AccelerometerService.class);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startService(intent);
            }
        });
        thread.start();
        startService(intent);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mCameraManager.setTorchMode("0", true);
                        count = 0;
                        textView.setText(count + "");
                    }

                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mCameraManager.setTorchMode("0", false);
                        count = 0;
                        textView.setText(count + "");
                    }

                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
