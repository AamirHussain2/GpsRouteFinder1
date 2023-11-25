package com.example.gpsroutefinder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gpsroutefinder.databinding.SpeedometerBinding;

public class Speedometer extends AppCompatActivity implements LocationListener {

    SpeedometerBinding speedometerBinding;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speedometerBinding = SpeedometerBinding.inflate(getLayoutInflater());
        setContentView(speedometerBinding.getRoot());

        setRunTimeActionBar();

        // Check if location services are available and if the GPS provider is enabled
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Request location permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                // Permission already granted, initialize location manager
                initializeLocationManager();
            }
        } else {
            // Handle the case where location services or GPS are not available
            Toast.makeText(this, "Location services or GPS is not available.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setRunTimeActionBar() {
        speedometerBinding.actionBar.settingBtn.setVisibility(View.GONE);
        speedometerBinding.actionBar.tvGps.setVisibility(View.GONE);
        speedometerBinding.actionBar.backArrow.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initialize location manager
                initializeLocationManager();
            } else {
                // Handle the case where the user denied the location permission
                Toast.makeText(this, "Location permission denied. The speedometer requires location access.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initializeLocationManager() {
        if (locationManager != null) {
            // Request location updates
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider requesting permissions or handling the case where permissions are denied
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            float speed = location.getSpeed(); // Speed is in meters per second
            float speedKmh = speed * 3.6f; // Convert to kilometers per hour
            speedometerBinding.speedView.speedTo(speedKmh);
        }
    }

    // Implement other required methods of the LocationListener interface
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }
}
