package com.example.gpsroutefinder.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private static final String PREF_NAME = "MyAppPreferences";
    private static final String KEY_INT_ARRAY = "intArray";
    private static final String KEY_STRING_VALUE = "stringValue";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public MySharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveStringValue(String value) {
        editor.putString(KEY_STRING_VALUE, value);
        editor.apply();
    }

    // Retrieve locale value from SharedPreferences
    public String getStringValue() {
        return sharedPreferences.getString(KEY_STRING_VALUE, "en"); // Empty string is the default value if the key is not found
    }

}
