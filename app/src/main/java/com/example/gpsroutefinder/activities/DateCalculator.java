package com.example.gpsroutefinder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.databinding.DateCalculatorBinding;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateCalculator extends AppCompatActivity {

    DateCalculatorBinding dateCalculatorBinding;
    private Calendar startCalendar;
    private Calendar endCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateCalculatorBinding = DateCalculatorBinding.inflate(getLayoutInflater());
        setContentView(dateCalculatorBinding.getRoot());

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        dateCalculatorBinding.startDate.setOnClickListener(view -> showStartDatePickerDialog());

        dateCalculatorBinding.endDate.setOnClickListener(view -> showEndDatePickerDialog());

        dateCalculatorBinding.calculate.setOnClickListener(view -> calculateAndDisplayTimeDifference());

        setRunTimeActionBar();

    }

    private void setRunTimeActionBar() {
        dateCalculatorBinding.actionBar.tvGps.setText(R.string.date_calculator);

        dateCalculatorBinding.actionBar.settingBtn.setVisibility(View.GONE);

        dateCalculatorBinding.actionBar.backArrow.setOnClickListener(view ->
                startActivity(new Intent(this, NavigationTools.class)));
    }

    private void showEndDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    endCalendar.set(Calendar.YEAR, year);
                    endCalendar.set(Calendar.MONTH, month);
                    endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Set the time of the end date to 00:00 AM
//                    endCalendar.set(Calendar.HOUR_OF_DAY, 0);
//                    endCalendar.set(Calendar.MINUTE, 0);
//                    endCalendar.set(Calendar.SECOND, 0);
//                    endCalendar.set(Calendar.MILLISECOND, 0);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String dateString = dateFormat.format(endCalendar.getTime());
                    dateCalculatorBinding.endDate.setText(dateString);
                },
                endCalendar.get(Calendar.YEAR),
                endCalendar.get(Calendar.MONTH),
                endCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showStartDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    startCalendar.set(Calendar.YEAR, year);
                    startCalendar.set(Calendar.MONTH, month);
                    startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String dateString = dateFormat.format(startCalendar.getTime());
                    dateCalculatorBinding.startDate.setText(dateString);
                },
                startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void calculateAndDisplayTimeDifference() {

        dateCalculatorBinding.startDateTv.setVisibility(View.GONE);
        dateCalculatorBinding.endDateTv.setVisibility(View.GONE);
        dateCalculatorBinding.calculate.setVisibility(View.GONE);
        dateCalculatorBinding.timeWithIn.setVisibility(View.VISIBLE);
        dateCalculatorBinding.viewDash.setVisibility(View.VISIBLE);

        dateCalculatorBinding.startDate.setClickable(false);
        dateCalculatorBinding.endDate.setClickable(false);

        if (startCalendar != null && endCalendar != null) {
            Date startDate = startCalendar.getTime();
            Date endDate = endCalendar.getTime();

            long timeDifferenceMillis = endDate.getTime() - startDate.getTime();
            long timeDifferenceSeconds = timeDifferenceMillis / 1000;
            long timeDifferenceMinutes = timeDifferenceSeconds / 60;
            long timeDifferenceHours = timeDifferenceMinutes / 60;
            long timeDifferenceDays = timeDifferenceHours / 24;
            long timeDifferenceWeeks = timeDifferenceDays / 7;

            // Calculate weekend days and business days
            int weekendDays = 0;
            int businessDays = 0;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            for (int i = 0; i <= timeDifferenceDays; i++) {
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                    weekendDays++;
                } else {
                    businessDays++;
                }
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            // Subtract weekends from total days to get business days
            businessDays = (int) timeDifferenceDays - weekendDays;

            Log.e("TAG", String.format(Locale.getDefault(),
                    "Days: %d, Hours: %d, Minutes: %d, Seconds: %d, Weeks: %d, WeekendDays: %d, BusinessDays: %d",
                    timeDifferenceDays, timeDifferenceHours, timeDifferenceMinutes,
                    timeDifferenceSeconds, timeDifferenceWeeks, weekendDays, businessDays));

            Bundle bundle = new Bundle();
            bundle.putInt("Days", (int) timeDifferenceDays);
            bundle.putInt("Hours", (int) timeDifferenceHours);
            bundle.putInt("Minutes", (int) timeDifferenceMinutes);
            bundle.putInt("Seconds", (int) timeDifferenceSeconds);
            bundle.putInt("Weeks", (int) timeDifferenceWeeks);
            bundle.putInt("WeekendDays", weekendDays);
            bundle.putInt("BusinessDays", businessDays);

            // set DateCalculatorFragment Arguments
            DateCalculatorFragment dateCalculatorFragment = new DateCalculatorFragment();
            dateCalculatorFragment.setArguments(bundle);

            // Replace the current fragment with the new fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flcontainer, dateCalculatorFragment);
            transaction.commit();
        }
    }

}
