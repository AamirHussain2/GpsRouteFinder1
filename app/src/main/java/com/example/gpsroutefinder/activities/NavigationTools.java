package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.databinding.NavigationToolsBinding;

public class NavigationTools extends AppCompatActivity {

    NavigationToolsBinding navigationToolsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationToolsBinding = NavigationToolsBinding.inflate(getLayoutInflater());
        setContentView(navigationToolsBinding.getRoot());

        setRunTimeActionBar();

        navigationToolsBinding.dateCalculator.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), DateCalculator.class)));

        navigationToolsBinding.translate.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), Translate.class)));

        navigationToolsBinding.compass.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), Compass.class)));

        navigationToolsBinding.speedometer.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), Speedometer.class)));

        navigationToolsBinding.worldClock.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), WorldClock.class)));

        navigationToolsBinding.selectLanguage.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), SelectLanguage.class)));

        navigationToolsBinding.actionBar.backArrow.setOnClickListener(view ->
                startActivity(new Intent(this, HomeScreen.class)));
    }

    private void setRunTimeActionBar() {

        navigationToolsBinding.actionBar.settingBtn.setVisibility(View.GONE);
        navigationToolsBinding.actionBar.tvGps.setText(R.string.navigationTools);
    }
}