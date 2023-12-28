package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gpsroutefinder.Interface.CityListCallbackAdapter;
import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.adapters.CityListAdapter;
import com.example.gpsroutefinder.adapters.CitySelectedListAdapter;
import com.example.gpsroutefinder.databinding.WorldClockBinding;
import com.example.gpsroutefinder.models.City;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WorldClock extends AppCompatActivity implements CityListCallbackAdapter {
    final int REQUEST_CODE = 1;
    WorldClockBinding worldClockBinding;
    ArrayList<City> cities;

    ArrayList<City> selected_cities;
    EditText text;
    RecyclerView cityList;
    BottomSheetDialog bottomSheetDialog;
    CityListAdapter adapter;
    ArrayList<Integer> dynamicIntArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        worldClockBinding = WorldClockBinding.inflate(getLayoutInflater());
        setContentView(worldClockBinding.getRoot());

        cities = new ArrayList<>();
        selected_cities = new ArrayList<>();

        callJSON(()-> {
            CreateListView(null);
            Log.e("tagCity","onCreate: "+cities);

        });

        worldClockBinding.floatingButton.setOnClickListener(view -> showBottomDialog());
        setRunTimeActionBar();

    }
    private void showBottomDialog() {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();

        // Create a BottomSheetDialog and set its content view to your custom layout
        bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogCity);
        //Inflate layout
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_world_clock, worldClockBinding.getRoot(), false);
        //set view
        bottomSheetDialog.setContentView(view);
        cityList = view.findViewById(R.id.worldClockBottomSheetRV);
        text = view.findViewById(R.id.editText);

        adapter = new CityListAdapter(this,this, cities);
        Log.e("tagcity","callBottomDialog: "+cities);

        cityList.setAdapter(adapter);

        Attach_Text_Listener(adapter);

        // Show the bottom sheet dialog
        bottomSheetDialog.show();
    }

    private void CreateListView(ArrayList<Integer> position) {

        if(position == null){
            return;
        }
        worldClockBinding.worldClockRV.setLayoutManager(new LinearLayoutManager(this));
        CitySelectedListAdapter citySelectedListAdapter = new CitySelectedListAdapter(this, cities, position);

        worldClockBinding.worldClockRV.setAdapter(citySelectedListAdapter);

        bottomSheetDialog.dismiss();
        Log.e("CreateListView", String.valueOf(position));
    }

    private void Attach_Text_Listener(CityListAdapter adapter) {

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void callJSON(Runnable customCallback){

        String url ="https://api.timezonedb.com/v2.1/list-time-zone?key=P8B5V3KL22GA&format=json";
        RequestQueue requestQueue = Volley.newRequestQueue(WorldClock.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("onResponse", String.valueOf(response));

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                try {
                    JSONArray Zones = jsonObject.getJSONArray("zones");

                    for (int i = 0; i < Zones.length(); i++) {

                        JSONObject zone = Zones.getJSONObject(i);

                        String countryCode = zone.getString("countryCode");
                        Log.e("Country Code:",countryCode);

                        String countryName = zone.getString("countryName");
                        Log.e("Country Name:",countryName);

                        String zoneName = zone.getString("zoneName");
                        Log.e("Zone Name:",zoneName);

                        int gmtOffset = zone.getInt("gmtOffset");
                        Log.e("GMT OffSet:", String.valueOf(gmtOffset));

                        int timestamp = zone.getInt("timestamp");
                        Log.e("Time Stamp:", String.valueOf(timestamp));

                        zoneName = zoneName.replace("\\", "");

                        String cityName = zoneName.split("/")[1];

                        City city = new City(cityName, zoneName);

                        cities.add(city);
                    }
                    Log.e("TAG","ArrayList<City>:cities:callJSON: "+cities);
                    customCallback.run();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the case where the response is null or empty
                Log.e("LoadCitiesTask", String.valueOf(error));
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onItemClicked(int position) {
        dynamicIntArray.add(position);
        CreateListView(dynamicIntArray);
        // Set the selected item at a particular position in the CitySelectedListAdapter
        Log.e("TAG", "onItemClicked: "+ position);
        Log.e("TAG", "Array: "+ dynamicIntArray);
    }

    private void setRunTimeActionBar() {

        worldClockBinding.actionBar.settingBtn.setVisibility(View.GONE);
        worldClockBinding.floatingButton.setVisibility(View.VISIBLE);
        worldClockBinding.actionBar.tvGps.setText(R.string.world_clock);

        worldClockBinding.actionBar.backArrow.setOnClickListener(view -> onBackPressed());

    }

}
