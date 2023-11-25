package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.databinding.LiveMapBinding;

public class LiveMap extends AppCompatActivity {

    LiveMapBinding liveMapBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liveMapBinding = LiveMapBinding.inflate(getLayoutInflater());
        setContentView(liveMapBinding.getRoot());

        setRunTimeActionBar();

        liveMapBinding.satelliteMap.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), SatelliteMap.class)));

        liveMapBinding.trafficAlerts.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), TrafficAlerts.class)));

        liveMapBinding.streetView.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), StreetView.class)));
    }

    private void setRunTimeActionBar() {

        liveMapBinding.actionBar.settingBtn.setVisibility(View.GONE);
        liveMapBinding.actionBar.tvGps.setText(R.string.live_map);

        liveMapBinding.actionBar.backArrow.setOnClickListener(view -> onBackPressed());
    }
}