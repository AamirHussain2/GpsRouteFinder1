package com.example.gpsroutefinder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.databinding.MyLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyLocation extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    List<Address> addressList;
    String locationAddress,locationAddress1;
    MyLocationBinding myLocationBinding;
    // to check whether sub FAB buttons are visible or not.
    Boolean isAllFabsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myLocationBinding = MyLocationBinding.inflate(getLayoutInflater());
        setContentView(myLocationBinding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        myLocationBinding.buildRoute.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), RouteFinder.class)));

        myLocationBinding.share.setOnClickListener(view -> shareAddress());

        // Now set all the FABs and all the action name texts as GONE
        myLocationBinding.fab1.setVisibility(View.GONE);
        myLocationBinding.fab2.setVisibility(View.GONE);
        myLocationBinding.fab3.setVisibility(View.GONE);
        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are invisible
        isAllFabsVisible = false;
        // visible only when Parent FAB button is clicked So
        // we have to handle the Parent FAB button first, by
        // using setOnClickListener you can see below
        myLocationBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAllFabsVisible) {
                    // when isAllFabsVisible becomes true make all
                    // the action name texts and FABs VISIBLE
                    myLocationBinding.fab1.show();
                    myLocationBinding.fab2.show();
                    myLocationBinding.fab3.show();
                    // make the boolean variable true as we
                    // have set the sub FABs visibility to GONE
                    isAllFabsVisible = true;
                } else {
                    // when isAllFabsVisible becomes true make
                    // all the action name texts and FABs GONE.
                    myLocationBinding.fab1.hide();
                    myLocationBinding.fab2.hide();
                    myLocationBinding.fab3.hide();

                    // make the boolean variable false as we
                    // have set the sub FABs visibility to GONE
                    isAllFabsVisible = false;
                }
            }
        });

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getCurrentLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        try
        {
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            Log.e("tag: ", String.valueOf(locationMode));

        } catch (Settings.SettingNotFoundException e) {

            Log.e("tag1", String.valueOf(e));
            e.printStackTrace();

        }
        return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }
    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location>  task = fusedLocationProviderClient.getLastLocation();

        if (isLocationEnabled(MyLocation.this)) {
            Log.e("TAG", "GPS is on");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                            Geocoder geocoder = new Geocoder(supportMapFragment.requireActivity(), Locale.getDefault());
                            try {
                                addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            try {
                                Log.e("locationAddress", "onMapReady: " + addressList.get(0).getLatitude());

                                locationAddress = addressList.get(0).getFeatureName();
                                locationAddress1 = addressList.get(0).getAddressLine(0);
                                String locationAddress2 = Double.toString(addressList.get(0).getLatitude());
                                String locationAddress3 = Double.toString(addressList.get(0).getLongitude());

                                myLocationBinding.header.setText(locationAddress);
                                myLocationBinding.address.setText(locationAddress1);
                                myLocationBinding.lat.setText(locationAddress2);
                                myLocationBinding.lon.setText(locationAddress3);

                            } catch (Exception e) {
                                Log.e("Exception ", String.valueOf(e));
                                e.printStackTrace();
                            }

                            //Place current location marker
                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(latLng)
                                    .title("i am here..")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                            myLocationBinding.fab1.setOnClickListener(view -> googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL));
                            myLocationBinding.fab2.setOnClickListener(view -> googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE));
                            myLocationBinding.fab3.setOnClickListener(view -> googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN));

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                            googleMap.isTrafficEnabled();
                            googleMap.getUiSettings().setAllGesturesEnabled(true);
                            googleMap.isMyLocationEnabled();

                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                    && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }

                            googleMap.setMyLocationEnabled(true);
                            googleMap.getUiSettings().setMapToolbarEnabled(true);
//                            googleMap.getUiSettings().setZoomControlsEnabled(true);


                        }
                    });
                }
            });

        }
        else
        {
            AlertDialog.Builder notifyLocationServices = new AlertDialog.Builder(MyLocation.this);
            notifyLocationServices.setTitle("Switch on Location Services");
            notifyLocationServices.setMessage("Location Services must be turned on to complete this action. Also please take note that if on a very weak network connection,  such as 'E' Mobile Data or 'Very weak Wifi-Connections' it may take even 15 mins to load. If on a very weak network connection as stated above, location returned to application may be null or nothing and cause the application to crash.");

            notifyLocationServices.setPositiveButton("Ok, Open Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent openLocationSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    MyLocation.this.startActivity(openLocationSettings);
                    finish();
                }
            });
            notifyLocationServices.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });
            notifyLocationServices.show();
        }

    }

    private void shareAddress() {
        // Create an Intent to open the share dialog
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, locationAddress1);
        // Start the share activity
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

}