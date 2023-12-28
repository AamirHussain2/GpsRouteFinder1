package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gpsroutefinder.Interface.SelectLanguageCallbackAdapter;
import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.adapters.SelectLanguageRVAdapter;
import com.example.gpsroutefinder.databinding.SelectLanguageBinding;
import com.example.gpsroutefinder.utils.LocaleHelper;
import com.example.gpsroutefinder.utils.MySharedPreferences;

public class SelectLanguage extends AppCompatActivity implements SelectLanguageCallbackAdapter {

    SelectLanguageBinding selectLanguageBinding;
    MySharedPreferences mySharedPreferences;
    int selectedPosition = -1;
    String[] languageCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectLanguageBinding = SelectLanguageBinding.inflate(getLayoutInflater());
        setContentView(selectLanguageBinding.getRoot());

        mySharedPreferences = new MySharedPreferences(this);

        String[] languageTitle = {"English","Arabic","Urdu","Chinese","French","German","Italian","Korean","Polish","Portuguese","Russian","Spanish","Thai","Turkish","Vietnamese"};
        String[] languageSubTitle = {" ","العربية","اردو","中国人","Français","Deutsch","Italiano","한국인","Polski","Português","русский","Español","แบบไทย","Türkçe","Tiếng Việt"};

        initLanguageCodeArray();

        selectedPosition = getUpdateSelectedPosition();

        SelectLanguageRVAdapter arrayAdapter = new SelectLanguageRVAdapter(getApplicationContext(), languageTitle, languageSubTitle, this, selectedPosition);
        selectLanguageBinding.listView.setAdapter(arrayAdapter);

        selectLanguageBinding.btnSelectLanguage.setOnClickListener(view -> clickSelectedLanguage());

        setRunTimeActionBar();

    }

    private void initLanguageCodeArray() {
        // Initialize languageCodes array before using it
        languageCodes = new String[]{
                "en", // English
                "ar", // Arabic
                "ur", // Urdu
                "zh", // Chinese
                "fr", // French
                "de", // German
                "it", // Italian
                "ko", // Korean
                "pl", // Polish
                "pt", // Portuguese
                "ru", // Russian
                "es", // Spanish
                "th", // Thai
                "tr", // Turkish
                "vi"  // Vietnamese
        };
    }

    private int getUpdateSelectedPosition() {
        // Fetch the saved language code from SharedPreferences
        String savedLanguageCode = mySharedPreferences.getStringValue(); // Assuming you have a method to retrieve the saved value
        Log.e("TAG", "savedLanguageCode:onCreate:SelectLanguage: "+savedLanguageCode);
        selectedPosition = getPositionForLanguageCode(savedLanguageCode); // Update selectedPosition based on the saved language code
        Log.e("TAG", "selectedPosition:onCreate:SelectLanguage: "+selectedPosition);
        return selectedPosition;
    }

    private int getPositionForLanguageCode(String languageCode) {
        Log.e("TAG","languageCode:getPositionForLanguageCode: "+languageCode);
        for (int i = 0; i < languageCodes.length; i++) {
            if (languageCodes[i].equals(languageCode)) {
                return i; // Return the position if language code matches
            }
        }
        return -1; // Return -1 if not found
    }

    private void clickSelectedLanguage() {
        if (selectedPosition == -1) {
            // If no language is selected, show a toast message
            Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show();
        } else {
            // Restart your app or the current activity to apply the language change.
            // You may need to reload resources like strings and layouts as well.
            restartApp();
        }
    }

    @Override
    public void onItemSelected(int position) {

        Log.e("TAG", "relativeLayout:selectedPosition: "+position);

        // Handle the language selection here (you can use selectedPosition)
        String selectedLanguageCode = getLanguageCodeForPosition(position);
        Log.e("TAG", "selectedLanguageCode:onItemSelected: "+selectedLanguageCode);
        mySharedPreferences.saveStringValue(selectedLanguageCode);
        setLocale(selectedLanguageCode);
        // Update the selectedPosition when a user clicks a specific position
        selectedPosition = position;
        Log.e("TAG","selectedPosition:onItemSelected: "+selectedPosition);
        // Notify the adapter that the dataset has changed
    }

    private String getLanguageCodeForPosition(int position) {
        // Implement this method to map the selected position to the corresponding language code.
        // You can use a predefined array of language codes or other logic based on your data.
        // For example:
//        languageCodes = new String[] {
//                "en", // English
//                "ar", // Arabic
//                "ur", // Urdu
//                "zh", // Chinese
//                "fr", // French
//                "de", // German
//                "it", // Italian
//                "ko", // Korean
//                "pl", // Polish
//                "pt", // Portuguese
//                "ru", // Russian
//                "es", // Spanish
//                "th", // Thai
//                "tr", // Turkish
//                "vi" // Vietnamese
//        };
        if (position >= 0 && position < languageCodes.length) {
            Log.e("TAG","position clicked: "+languageCodes[position]);
            return languageCodes[position];
        } else {
            // Return a default language code or handle the error as needed.
            return "en"; // Default to English if the position is out of bounds.
        }
    }

    private void setLocale(String languageCode) {
        LocaleHelper.setLocale(this, languageCode);
    }

    private void restartApp() {
        // Restart your app or the current activity to apply the language change.
        // You may need to reload resources like strings and layouts as well.
        // Restarting the current activity is demonstrated here.
        Intent intent = new Intent(this , MainActivity.class);
        finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    private void setRunTimeActionBar() {
        selectLanguageBinding.actionBar.settingBtn.setVisibility(View.GONE);
        selectLanguageBinding.actionBar.tvGps.setText(R.string.select_language);

        selectLanguageBinding.actionBar.backArrow.setOnClickListener(view -> onBackPressed());

    }

}