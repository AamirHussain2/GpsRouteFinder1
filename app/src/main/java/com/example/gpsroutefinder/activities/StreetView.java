package com.example.gpsroutefinder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gpsroutefinder.R;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StreetView extends AppCompatActivity implements  OnStreetViewPanoramaReadyCallback{

    // George St, Sydney
//    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private StreetViewPanorama streetViewPanorama;
    private boolean secondLocation = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.street_view);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.googleMapStreetView);

        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
        FloatingActionButton fab  = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondLocation = !secondLocation;
                onStreetViewPanoramaReady(streetViewPanorama);
            }
        });


    }

    @Override
    public void onStreetViewPanoramaReady(@NonNull StreetViewPanorama streetViewPanorama) {

        if(secondLocation){
            streetViewPanorama.setPosition(new LatLng(-33.87365, 151.20689), StreetViewSource.OUTDOOR);

        }else {
            streetViewPanorama.setPosition(new LatLng(-33.87365, 151.20689));
        }

        streetViewPanorama.setStreetNamesEnabled(true);
        streetViewPanorama.setPanningGesturesEnabled(true);
        streetViewPanorama.setZoomGesturesEnabled(true);
        streetViewPanorama.setUserNavigationEnabled(true);

        streetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder().orientation(new StreetViewPanoramaOrientation(20,20))
                        .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                        .build(),2000
        );
        streetViewPanorama.setOnStreetViewPanoramaCameraChangeListener(panoramaChangelistener);

    }
    private  StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener panoramaChangelistener = new StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener() {
        @Override
        public void onStreetViewPanoramaCameraChange(@NonNull StreetViewPanoramaCamera streetViewPanoramaCamera) {
            Toast.makeText(StreetView.this, "location updated", Toast.LENGTH_SHORT).show();
        }
    };
}

