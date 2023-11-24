package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.databinding.CompassBinding;

public class Compass extends AppCompatActivity implements SensorEventListener {

    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private float azimuth = 0f;
    private float currectAzimuth = 0f;
    private SensorManager mSensorManager;
    CompassBinding compassBinding;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compassBinding = CompassBinding.inflate(getLayoutInflater());
        setContentView(compassBinding.getRoot());

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        setRunTimeActionBar();

    }

    private void setRunTimeActionBar() {
        compassBinding.actionBar.settingBtn.setVisibility(View.GONE);
        compassBinding.actionBar.tvGps.setVisibility(View.GONE);
        compassBinding.actionBar.toolBar.setBackgroundColor(Color.TRANSPARENT);
//        compassBinding.actionBar.toolBar.setBackground(getResources().);
        compassBinding.actionBar.backArrow.setOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        final float alpha = 0.97f;
        synchronized (this) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mGravity[0] = alpha * mGravity[0] + (1 - alpha) * sensorEvent.values[0];
                mGravity[1] = alpha * mGravity[1] + (1 - alpha) * sensorEvent.values[1];
                mGravity[2] = alpha * mGravity[2] + (1 - alpha) * sensorEvent.values[2];
            }

            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha) * sensorEvent.values[0];
                mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha) * sensorEvent.values[1];
                mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha) * sensorEvent.values[2];
            }
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = (float) Math.toDegrees(orientation[0]);
                azimuth = (azimuth + 360) % 360;

                Animation anim = new RotateAnimation(-currectAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f
                        , Animation.RELATIVE_TO_SELF, 0.5f);
                currectAzimuth = azimuth;
                anim.setDuration(500);
                anim.setRepeatCount(0);
                anim.setFillAfter(true);

                compassBinding.compass.startAnimation(anim);

                if (azimuth >= 337.5 && azimuth <= 22.5) {
                    compassBinding.textDegree.setText((int) currectAzimuth + "\u00B0" + "N");
                } else if (azimuth >= 22.5 && azimuth <= 67.5) {
                    compassBinding.textDegree.setText((int) currectAzimuth + "\u00B0" + "NE");
                } else if (azimuth >= 67.5 && azimuth <= 112.5) {
                    compassBinding.textDegree.setText((int) currectAzimuth + "\u00B0" + "E");
                } else if (azimuth >= 112.5 && azimuth <= 157.5) {
                    compassBinding.textDegree.setText((int) currectAzimuth + "\u00B0" + "SE");
                } else if (azimuth >= 157.5 && azimuth <= 202.5) {
                    compassBinding.textDegree.setText((int) currectAzimuth + "\u00B0" + "S");
                } else if (azimuth >= 202.5 && azimuth <= 247.5) {
                    compassBinding.textDegree.setText((int) currectAzimuth + "\u00B0" + "SW");
                } else if (azimuth >= 247.5 && azimuth <= 292.5) {
                    compassBinding.textDegree.setText((int) currectAzimuth + "\u00B0" + "W");
                } else if (azimuth >= 292.5 && azimuth < 337.5) {
                    compassBinding.textDegree.setText((int) currectAzimuth + "\u00B0" + "NW");
                }else {
                    compassBinding.textDegree.setText((int) currectAzimuth+"\u00B0"+"N");
                }
            }

        }
    }

        @Override
        public void onAccuracyChanged (Sensor sensor,int accuracy){

        }
    }
