package com.example.gpsroutefinder.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.databinding.RouteFinderBinding;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class RouteFinder extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQ_CODE_SPEECH_INPUT_2 = 101;
    Animation animation;
    RouteFinderBinding routeFinderBinding;
    @SuppressLint("ClickableViewAccessibility")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeFinderBinding = RouteFinderBinding.inflate(getLayoutInflater());
        setContentView(routeFinderBinding.getRoot());

        setRunTimeActionBar();

        routeFinderBinding.voiceLocation.setOnClickListener(view -> promptSpeechInput(REQ_CODE_SPEECH_INPUT));
        routeFinderBinding.voiceDestination.setOnClickListener(view -> promptSpeechInput(REQ_CODE_SPEECH_INPUT_2));
        routeFinderBinding.showRoute.setOnClickListener(view -> showRoute());

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_rotate);

        routeFinderBinding.compareArrow.setOnClickListener(view -> {

                routeFinderBinding.compareArrow.startAnimation(animation);

                String temp = Objects.requireNonNull(routeFinderBinding.edLocation.getText()).toString();

                routeFinderBinding.edLocation.setText(routeFinderBinding.edDestination.getText());
                routeFinderBinding.edDestination.setText(temp);

                routeFinderBinding.imgLoc.setImageResource(R.drawable.loc_btn_icon);
                routeFinderBinding.imgDes.setImageResource(R.drawable.des_btn_icon);

        });

    }

    private void setRunTimeActionBar() {
        routeFinderBinding.actionBar.backArrow.setVisibility(View.VISIBLE);
        routeFinderBinding.actionBar.settingBtn.setVisibility(View.GONE);
        routeFinderBinding.actionBar.tvGps.setText("Route Finder");
        routeFinderBinding.actionBar.backArrow.setOnClickListener(view -> onBackPressed());
    }

    private void promptSpeechInput(int CODE) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something...");

        try {
            startActivityForResult(intent, CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Speech recognition not supported on your device", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null && result.size() > 0) {
                    String spokenText = result.get(0);
                    routeFinderBinding.edLocation.setText(spokenText);

                }
            }
        }else {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null && result.size() > 0) {
                    String spokenText = result.get(0);
                    routeFinderBinding.edDestination.setText(spokenText);

                }
            }
        }
    }

    private void showRoute() {

        if (Objects.requireNonNull(routeFinderBinding.edLocation.getText()).toString().isEmpty()
                || Objects.requireNonNull(routeFinderBinding.edDestination.getText()).toString().isEmpty()) {

            Toast.makeText(this, "location or destination is empty.", Toast.LENGTH_SHORT).show();

        } else if (Objects.requireNonNull(routeFinderBinding.edLocation.getText()).toString().equals(" ")
                || Objects.requireNonNull(routeFinderBinding.edDestination.getText()).toString().equals(" ")){

            Toast.makeText(this, "location or destination is empty.", Toast.LENGTH_SHORT).show();

        } else {
            Uri uri = Uri.parse("https://www.google.com/maps/dir/" +routeFinderBinding.edLocation.getText().toString() + "/"
                    +routeFinderBinding.edDestination.getText().toString());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
            mapIntent.setPackage("com.google.android.apps.maps");
            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mapIntent);
        }

    }

}