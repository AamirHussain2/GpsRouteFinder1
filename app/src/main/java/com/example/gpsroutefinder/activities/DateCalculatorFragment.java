package com.example.gpsroutefinder.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.databinding.FragmentDateCalculatorBinding;

public class DateCalculatorFragment extends Fragment {
FragmentDateCalculatorBinding dateCalculatorBinding;
    int Days,Hours,Minutes,Seconds,Weeks,WeekendDays,BusinessDays;
    public DateCalculatorFragment() {
        // Required empty public constructor
    }


    public static DateCalculatorFragment newInstance(String param1, String param2) {
        return new DateCalculatorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dateCalculatorBinding = FragmentDateCalculatorBinding.inflate(getLayoutInflater(), container, false);

        dateCalculatorBinding.share.setOnClickListener(view -> share());

        if(getArguments() != null) {
             Days = getArguments().getInt("Days");
             Hours = getArguments().getInt("Hours");
             Minutes = getArguments().getInt("Minutes");
             Seconds = getArguments().getInt("Seconds");
             Weeks = getArguments().getInt("Weeks");
             WeekendDays = getArguments().getInt("WeekendDays");
             BusinessDays = getArguments().getInt("BusinessDays");
//            int Months = getArguments().getInt("Months");

            dateCalculatorBinding.tvDays.setText(Integer.toString(Days));
            dateCalculatorBinding.tvHours.setText(Integer.toString(Hours));
            dateCalculatorBinding.tvMinutes.setText(Integer.toString(Minutes));
            dateCalculatorBinding.tvSecond.setText(Integer.toString(Seconds));
            dateCalculatorBinding.tvWeeks.setText(Integer.toString(Weeks));
            dateCalculatorBinding.tvWeekends.setText(Integer.toString(WeekendDays));
            dateCalculatorBinding.tvBusinessDays.setText(Integer.toString(BusinessDays));
//            dateCalculatorBinding.tvSecond.setText(Integer.toString(Months));
        }

        dateCalculatorBinding.reSet.setOnClickListener(view ->
                startActivity(new Intent(getActivity(), DateCalculator.class)));

        return dateCalculatorBinding.getRoot();
    }

    private void share() {
        // Create an Intent to open the share dialog
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "It seems that "+Days+" days can be converted to one of these units:"+"\n"+Seconds+" Seconds"+"\n"+Minutes+" Minutes"
                                 +"\n"+Hours+" Hours"+"\n"+Days+" Days"+"\n"+Weeks+" Weeks"+"\n"+WeekendDays+" Weekend Days"+"\n"+BusinessDays+" Business Days"+"\n"+"\n"
                                 +"https://play.google.com/store/apps/details?id=map.ly.gps.navigation.route.planer");
        // Start the share activity
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}