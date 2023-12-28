package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.widget.Toast;


import com.example.gpsroutefinder.databinding.SearchLocationBinding;

public class SearchLocation extends AppCompatActivity {

    SearchLocationBinding searchLocationBinding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchLocationBinding = SearchLocationBinding.inflate(getLayoutInflater());
        setContentView(searchLocationBinding.getRoot());

        searchLocationBinding.backArrow.setOnClickListener(view -> onBackPressed());

        searchLocationBinding.voiceNavigation.setOnClickListener(view ->
                Toast.makeText(SearchLocation.this, "voice click", Toast.LENGTH_SHORT).show());
    }
}