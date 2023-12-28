package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.adapters.ExploreNearByRVAdapter;
import com.example.gpsroutefinder.databinding.NearbyPlacesBinding;
import com.example.gpsroutefinder.models.ExploreNearByRVModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class NearbyPlaces extends AppCompatActivity {

    ArrayList<ExploreNearByRVModel> arrayList;
    FusedLocationProviderClient fusedLocationProviderClient;

    NearbyPlacesBinding nearbyPlacesBinding;

    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nearbyPlacesBinding = NearbyPlacesBinding.inflate(getLayoutInflater());
        setContentView(nearbyPlacesBinding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

//        Bundle bundle = getIntent().getExtras();
//        String latLng = bundle.getString("latLng",0);
//
//        Uri gmmIntentUri = Uri.parse("geo:" +latLng + "?q=hospital");
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        startActivity(mapIntent);

        getCurrentLocation();

        setRunTimeActionBar();

        nearbyPlacesBinding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        arrayList = new ArrayList<>();

        arrayList.add(new ExploreNearByRVModel(R.drawable.gas_station, "Gas Station"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.restaurant, "Restaurant"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.cafe, "Cafe"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.convenience_store, "Convenience Store"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.atm, "ATM"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.pharmacy, "Pharmacy"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.lodging, "Lodging"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.parking, "Parking"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.bus_station, "Bus Station"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.bar, "Bar"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.hospital, "Hospital"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.bank, "Bank"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.stadium, "Stadium"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.police, "Police"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.church, "Church"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.airport, "Airport"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.shopping_mall, "Shopping Mall"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.taxi_stand, "Taxi Stand"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.post_office, "Post Office"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.train_station, "Train Station"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.gym, "Gym"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.zoo, "Zoo"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.night_club, "Night Club"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.pet_store, "Pet Store"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.subway_station, "Subway Station"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.movie_theater, "Movie Theater"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.bakery, "Bakery"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.park, "Park"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.beauty_saloon, "Beauty Saloon"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.museum, "Museum"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.hair_care, "Hair Care"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.department_store, "Department Store"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.library, "Library"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.hardware_store, "Hardware Store"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.amusement_park, "Amusement Park"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.court_house, "Courthouse"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.jewelry_store, "jewelry Store"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.hindu_temple, "Hindu Temple"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.laundry, "Laundry"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.art_gallery, "Art Gallery"));
        arrayList.add(new ExploreNearByRVModel(R.drawable.book_store, "Book Store"));

        ExploreNearByRVAdapter nearByRecyclerAdapter = new ExploreNearByRVAdapter(getApplicationContext(), arrayList, latLng);
        nearbyPlacesBinding.recyclerView.setAdapter(nearByRecyclerAdapter);

    }

    private void setRunTimeActionBar() {
        nearbyPlacesBinding.actionBar.settingBtn.setVisibility(View.GONE);
        nearbyPlacesBinding.actionBar.tvGps.setText(R.string.exploreNearby);
        nearbyPlacesBinding.actionBar.backArrow.setOnClickListener(view -> onBackPressed());
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());

            }
        });
    }
}