package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.databinding.NumberLocatorBinding;
import com.hbb20.CountryCodePicker;

public class NumberLocator extends AppCompatActivity {
    NumberLocatorBinding numberLocatorBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numberLocatorBinding = NumberLocatorBinding.inflate(getLayoutInflater());
        setContentView(numberLocatorBinding.getRoot());

        numberLocatorBinding.countryCode.registerCarrierNumberEditText(numberLocatorBinding.mobileNumber);


        setRunTimeActionBar();

        numberLocatorBinding.findLocation.setOnClickListener(view -> {
            if(!numberLocatorBinding.countryCode.isValidFullNumber()){
                numberLocatorBinding.mobileNumber.setError("Phone number not valid");
                return;
            }

            Toast.makeText(NumberLocator.this, "Find Location Clicked", Toast.LENGTH_SHORT).show();

        });

    }

    private void setRunTimeActionBar() {
        numberLocatorBinding.actionBar.tvGps.setVisibility(View.GONE);
        numberLocatorBinding.actionBar.settingBtn.setVisibility(View.GONE);
        numberLocatorBinding.actionBar.backArrow.setOnClickListener(view -> onBackPressed());
        numberLocatorBinding.actionBar.toolBar.setBackgroundColor(getColor(R.color.white));

    }

}