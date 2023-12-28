package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.databinding.HomeScreenBinding;
import com.example.gpsroutefinder.databinding.NumberLocatorBinding;
import com.example.gpsroutefinder.utils.LocaleHelper;
import com.example.gpsroutefinder.utils.MySharedPreferences;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

public class HomeScreen extends AppCompatActivity {

    HomeScreenBinding homeScreenBinding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeScreenBinding = HomeScreenBinding.inflate(getLayoutInflater());
        setContentView(homeScreenBinding.getRoot());

        homeScreenBinding.actionBar.backArrow.setVisibility(View.GONE);

        homeScreenBinding.numberLocator.setOnClickListener(view ->
                startActivity(new Intent(HomeScreen.this, NumberLocator.class)));

        homeScreenBinding.actionBar.settingBtn.setOnClickListener(view ->
                startActivity(new Intent(HomeScreen.this, SettingScreen.class)));

        homeScreenBinding.nearbyPlaces.setOnClickListener(view ->
                startActivity(new Intent(HomeScreen.this, NearbyPlaces.class)));

        homeScreenBinding.navigationTools.setOnClickListener(view ->
                startActivity(new Intent(HomeScreen.this, NavigationTools.class)));

        homeScreenBinding.liveMap.setOnClickListener(view ->
                startActivity(new Intent(HomeScreen.this, LiveMap.class)));

        homeScreenBinding.myLocation.setOnClickListener(view ->
                startActivity(new Intent(HomeScreen.this, MyLocation.class)));

        homeScreenBinding.findRoute.setOnClickListener(view ->
                startActivity(new Intent(HomeScreen.this, RouteFinder.class)));

        homeScreenBinding.starting.setOnClickListener(view ->
                startActivity(new Intent(HomeScreen.this, RouteFinder.class)));

        homeScreenBinding.destination.setOnClickListener(view ->
                startActivity(new Intent(HomeScreen.this, RouteFinder.class)));

        homeScreenBinding.voiceNavigation.setOnClickListener(view ->
                startActivity(new Intent(HomeScreen.this, VoiceNavigation.class)));

        homeScreenBinding.weather.setOnClickListener(view ->
                startActivity(new Intent(HomeScreen.this, Weather.class)));

//        //step 1
//        MobileAds.initialize(this);
//        //step 2
//        AdRequest adRequest = new AdRequest.Builder().build();
//        //step 3
//        homeScreenBinding.adView.loadAd(adRequest);

    }
}