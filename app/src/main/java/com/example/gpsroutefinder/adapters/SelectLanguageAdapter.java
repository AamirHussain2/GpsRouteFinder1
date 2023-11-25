package com.example.gpsroutefinder.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.activities.HomeScreen;
import com.example.gpsroutefinder.utils.LocaleHelper;

public class SelectLanguageAdapter extends BaseAdapter {
    Context context;
    Activity activity;
    String[] languageTitle;
    String[] languageSubTitle;
    LayoutInflater inflater;
    private int selectedPosition = -1;

    public SelectLanguageAdapter(Context context, Activity activity, String[] languageTitle, String[] languageSubTitle) {
        this.context = context;
        this.activity = activity;
        this.languageTitle = languageTitle;
        this.languageSubTitle = languageSubTitle;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return languageTitle.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_selectlanguage_listview, null);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvSubTitle = view.findViewById(R.id.tvSubTitle);
        ImageView radio1 = view.findViewById(R.id.radio);
        RelativeLayout relativeLayout = view.findViewById(R.id.relativeLayout);

        tvTitle.setText(languageTitle[position]);
        tvSubTitle.setText(languageSubTitle[position]);

        if (position == selectedPosition) {
            radio1.setImageResource(R.drawable.radio_enable);
        } else {
            radio1.setImageResource(R.drawable.radio_disable);
        }

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the selected position
                selectedPosition = position;
                notifyDataSetChanged(); // Refresh the list view

                // Handle the language selection here (you can use selectedPosition)
                String selectedLanguageCode = getLanguageCodeForPosition(position);
                Log.e("TAG: selectedLanguageCode", "onClick: "+selectedLanguageCode);
                setLocale(selectedLanguageCode);
                // Restart your app or the current activity to apply the language change.
                // You may need to reload resources like strings and layouts as well.
                restartApp();
                Toast.makeText(context, "Clicked: " + languageTitle[position], Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private String getLanguageCodeForPosition(int position) {
        // Implement this method to map the selected position to the corresponding language code.
        // You can use a predefined array of language codes or other logic based on your data.
        // For example:
        String[] languageCodes = new String[] {
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
                "vi" // Vietnamese
                // Add more language codes as needed
        };

        if (position >= 0 && position < languageCodes.length) {
            return languageCodes[position];
        } else {
            // Return a default language code or handle the error as needed.
            return "en"; // Default to English if the position is out of bounds.
        }
    }

    private void setLocale(String languageCode) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            LocaleHelper.setLocale(activity, languageCode);
        }
    }

    private void restartApp() {
        // Restart your app or the current activity to apply the language change.
        // You may need to reload resources like strings and layouts as well.
        // Restarting the current activity is demonstrated here.
        Intent intent = new Intent(context, HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }

}