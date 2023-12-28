package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.utils.LocaleHelper;
import com.example.gpsroutefinder.utils.MySharedPreferences;

public class MainActivity extends AppCompatActivity {

    long SPLASH_TIME = 5000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("TAG","onCreate Method Called...");

        setLocale();

        startSplashTimer();



    }

    private void startSplashTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG","startSplashTimer:loadMethod...");
//                setLocale();
                Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);
    }

     private void setLocale() {

        MySharedPreferences mySharedPreferences = new MySharedPreferences(this);
        String localeValue = mySharedPreferences.getStringValue();
        Log.e("TAG", "localValue: " + localeValue);

        LocaleHelper.updateResourcesLegacy(this, localeValue);
    }
}
