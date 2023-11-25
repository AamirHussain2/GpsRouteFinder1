package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.databinding.WorldClockBinding;

public class WorldClock extends AppCompatActivity {

    WorldClockBinding worldClockBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        worldClockBinding = WorldClockBinding.inflate(getLayoutInflater());
        setContentView(worldClockBinding.getRoot());

        setRunTimeActionBar();

    }

    private void setRunTimeActionBar() {

        worldClockBinding.actionBar.settingBtn.setVisibility(View.GONE);
        worldClockBinding.floatingButton.setVisibility(View.VISIBLE);
        worldClockBinding.actionBar.tvGps.setText(R.string.world_clock);

        worldClockBinding.actionBar.backArrow.setOnClickListener(view -> onBackPressed());
    }
}