package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.databinding.SettingScreenBinding;

public class SettingScreen extends AppCompatActivity {

    SettingScreenBinding settingScreenBinding;
    // Get the phone model
    String phoneModel = Build.MODEL;

    // Get the Android SDK version
    int sdkVersion = Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingScreenBinding = SettingScreenBinding.inflate(getLayoutInflater());
        setContentView(settingScreenBinding.getRoot());

        setRunTimeActionBar();

        settingScreenBinding.share.setOnClickListener(view ->{
            // Create an Intent to open the share dialog
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Sharing this text...");
            // Start the share activity
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

        settingScreenBinding.feedback.setOnClickListener(view ->{
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/email");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "your@email.com" });
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            intent.putExtra(Intent.EXTRA_TEXT, "Model: "+phoneModel +"\n"+"SDK: "+sdkVersion +"\n"+"Feedback: ");

            try {
                startActivity(Intent.createChooser(intent, "Send Feedback via..."));
            } catch (ActivityNotFoundException e) {
                // Handle error, no email app installed.
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void setRunTimeActionBar() {
        settingScreenBinding.actionBar.settingBtn.setVisibility(View.GONE);
        settingScreenBinding.actionBar.tvGps.setText(R.string.setting);
        settingScreenBinding.actionBar.backArrow.setOnClickListener(view -> onBackPressed());

    }
}