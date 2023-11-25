package com.example.gpsroutefinder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gpsroutefinder.Interface.MyAdapterCallback;
import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.databinding.TranslateBinding;
import com.example.gpsroutefinder.models.ModelLanguage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.gpsroutefinder.adapters.LanguagesRecyclerAdapter;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class Translate extends AppCompatActivity implements MyAdapterCallback {

    TranslateBinding translateBinding;
    boolean isChecked;
    BottomSheetDialog bottomSheetDialog;
    RecyclerView recyclerView;
    ArrayList<String> arrayList;
    String[] myStringArray;
    int left=12, right=56;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        translateBinding = TranslateBinding.inflate(getLayoutInflater());
        setContentView(translateBinding.getRoot());

        setRunTimeActionBar();

        //Instance of ArrayList
        arrayList = new ArrayList<>();

        myStringArray = getResources().getStringArray(R.array.search_language);

        arrayList.addAll(Arrays.asList(myStringArray));

        translateBinding.languageFrom.setOnClickListener(view -> {
            isChecked=true;
            showBottomSheetDialog();
        });
        translateBinding.languageFrom.setText(arrayList.get(12));

        translateBinding.languageTo.setOnClickListener(view ->{
            isChecked=false;
            showBottomSheetDialog();
        });
        translateBinding.languageTo.setText(arrayList.get(56));

        //set all text is empty
        translateBinding.deleteEditText.setOnClickListener(view -> translateBinding.editText.setText(""));
        translateBinding.deleteTextview.setOnClickListener(view -> translateBinding.textView.setText(""));

        translateBinding.swapBtn.setOnClickListener(view ->{

            int[] swappedStrings = swapStrings(left, right);
            translateBinding.languageFrom.setText(arrayList.get(swappedStrings[0]));

            translateBinding.languageTo.setText(arrayList.get(swappedStrings[1]));
            left=swappedStrings[0];
            right=swappedStrings[1];
        });

        translateBinding.share.setOnClickListener(view -> {
            // Create an Intent to open the share dialog
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Sharing this text...");
            // Start the share activity
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

        translateBinding.translate.setOnClickListener(view -> clickTranslator());
        translateBinding.copyContent.setOnClickListener(view -> setClipboard(getApplicationContext(),translateBinding.textView.getText().toString()));

    }

    private void setRunTimeActionBar() {
        translateBinding.actionBar.settingBtn.setVisibility(View.GONE);
        translateBinding.actionBar.tvGps.setText(R.string.translator);
        translateBinding.actionBar.backArrow.setOnClickListener(view -> onBackPressed());
    }

    private void setClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "copy text", Toast.LENGTH_SHORT).show();
    }

    private void clickTranslator() {

        List<String> languageCodeList = TranslateLanguage.getAllLanguages();

        for (String languageCode: languageCodeList){
            String languageTitle = new Locale(languageCode).getDisplayLanguage();

            Log.e("MAIN_TAG_languageCode","loadAvailableLanguage: languageCode: "+languageCode);
            Log.e("MAIN_TAG_languageTitle","loadAvailableLanguage: languageTitle: "+languageTitle);
            Log.e("MAIN_TAG_languageCodeList","loadAvailableLanguage: languageCodeList: "+languageCodeList.size());

            ModelLanguage modelLanguage = new ModelLanguage(languageCode, languageTitle);

        }
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(String.valueOf(right))
                        .build();

        Log.e("TAG: setSourceLanguage", String.valueOf(left));
        Log.e("TAG: setTargetLanguage", String.valueOf(right));

        Translator translator = Translation.getClient(options);

        String textToTranslate = translateBinding.editText.getText().toString();

        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();

        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // Model downloaded successfully. Okay to start translating.
                // (Set a flag, unhidden the translation UI, etc.)
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Model couldn’t be downloaded or other internal error.

            }
        });
        translator.translate(textToTranslate)
                .addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                translateBinding.textView.setText(s);
                Log.e("TAG: onSuccess", "Translated text: " + s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                translateBinding.textView.setText(e.getMessage());
                Log.e("TAG: onFailure", "Translated text: " + e);
            }
        });

    }

    public static int[] swapStrings(int str1, int str2) {
        int temp = str1;
        str1 = str2;
        str2 = temp;
        //int[] swappedStrings = {str1, str2};
        return new int[]{str1, str2};
    }

    private void showBottomSheetDialog() {
        // Create a BottomSheetDialog and set its content view to your custom layout
         bottomSheetDialog = new BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme);
        //Inflate layout
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, translateBinding.getRoot(), false);
        //set view
        bottomSheetDialog.setContentView(view);
        //Define RecyclerView
         recyclerView = view.findViewById(R.id.languagesRecyclerView);
        //create instance of adapter
        LanguagesRecyclerAdapter recyclerAdapter = new LanguagesRecyclerAdapter(getApplicationContext(), arrayList,this, isChecked);
        //set adapter in recyclerView
        recyclerView.setAdapter(recyclerAdapter);


        SearchView searchLanguage = view.findViewById(R.id.searchLanguage);

        searchLanguage.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                recyclerAdapter.getFilter().filter(text);
                return true;

            }
        });

        //Create LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Show the bottom sheet dialog
        bottomSheetDialog.show();

    }

    @Override
    public void onItemClicked(int position, Boolean isChecked) {
        bottomSheetDialog.dismiss();
        Log.e("TAG: onItemClicked", String.valueOf(position));
        // Handle the item click event here
        // You can perform any actions you want based on the callback
        if (isChecked){
            translateBinding.languageFrom.setText(arrayList.get(position));
            left=position;
        }else {
            translateBinding.languageTo.setText(arrayList.get(position));
            right=position;

        }
    }
}