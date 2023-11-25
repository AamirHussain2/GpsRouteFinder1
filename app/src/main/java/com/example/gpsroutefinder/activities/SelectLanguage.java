package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.adapters.SelectLanguageAdapter;
import com.example.gpsroutefinder.databinding.SelectLanguageBinding;
import com.example.gpsroutefinder.utils.LocaleHelper;
import com.google.mlkit.nl.translate.TranslateLanguage;

public class SelectLanguage extends AppCompatActivity {

    SelectLanguageBinding selectLanguageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectLanguageBinding = SelectLanguageBinding.inflate(getLayoutInflater());
        LocaleHelper.loadLocale(this);
        setContentView(selectLanguageBinding.getRoot());

        String[] languageTitle = {"English","Arabic","Urdu","Chinese","French","German","Italian","Korean","Polish","Portuguese","Russian","Spanish","Thai","Turkish","Vietnamese"};
        String[] languageSubTitle = {" ","العربية","اردو","中国人","Français","Deutsch","Italiano","한국인","Polski","Português","русский","Español","แบบไทย","Türkçe","Tiếng Việt"};

        setRunTimeActionBar();

        SelectLanguageAdapter arrayAdapter = new SelectLanguageAdapter(getApplicationContext(), this, languageTitle,languageSubTitle);
        selectLanguageBinding.listView.setAdapter(arrayAdapter);

    }

    private void setRunTimeActionBar() {
        selectLanguageBinding.actionBar.settingBtn.setVisibility(View.GONE);
        selectLanguageBinding.actionBar.tvGps.setText(R.string.select_language);

        selectLanguageBinding.actionBar.backArrow.setOnClickListener(view -> onBackPressed());
    }
}