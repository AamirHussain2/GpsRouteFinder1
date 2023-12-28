package com.example.gpsroutefinder.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gpsroutefinder.Interface.SelectLanguageCallbackAdapter;
import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.utils.MySharedPreferences;

public class SelectLanguageRVAdapter extends BaseAdapter {
    Context context;
    String[] languageTitle;
    String[] languageSubTitle;
    LayoutInflater inflater;
    int selectedPosition;
    MySharedPreferences mySharedPreferences;
    SelectLanguageCallbackAdapter selectLanguageCallbackAdapter;

    public SelectLanguageRVAdapter(Context context, String[] languageTitle, String[] languageSubTitle, SelectLanguageCallbackAdapter selectLanguageCallbackAdapter, int selectedPosition) {
        this.context = context;
        this.languageTitle = languageTitle;
        this.languageSubTitle = languageSubTitle;
        this.selectLanguageCallbackAdapter = selectLanguageCallbackAdapter;
        this.selectedPosition = selectedPosition;

        inflater = (LayoutInflater.from(context));
        mySharedPreferences = new MySharedPreferences(context);
    }

    @Override
    public int getCount() {
        return languageTitle.length;
    }

    @Override
    public Object getItem(int i) {
        return languageTitle[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        TextView tvTitle;
        TextView tvSubTitle;
        ImageView radio1;
        RelativeLayout relativeLayout;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Log.e("TAG", "getView:position: "+position);
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.custom_selectlanguage_listview, null);

            holder = new ViewHolder();
            holder.tvTitle = view.findViewById(R.id.tvTitle);
            holder.tvSubTitle = view.findViewById(R.id.tvSubTitle);
            holder.radio1 = view.findViewById(R.id.radio);
            holder.relativeLayout = view.findViewById(R.id.relativeLayout);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Always update the text views regardless of view recycling
        holder.tvTitle.setText(languageTitle[position]);
        holder.tvSubTitle.setText(languageSubTitle[position]);


        holder.relativeLayout.setOnClickListener(view1 -> {
            selectedPosition = position;

            if (selectLanguageCallbackAdapter != null) {
                Log.e("TAG", "callbacks: " + position);
                selectLanguageCallbackAdapter.onItemSelected(selectedPosition);
                notifyDataSetChanged(); // Notify the adapter that the data set has changed
            }
        });

        Log.e("TAG","selectedPosition: "+selectedPosition);
        // Update the radio button based on the selected position
        if (selectedPosition == position) {
            holder.radio1.setImageResource(R.drawable.radio_enable);
        } else {
            holder.radio1.setImageResource(R.drawable.radio_disable);
        }

        return view;
    }

}