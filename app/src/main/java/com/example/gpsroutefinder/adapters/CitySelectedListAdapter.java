package com.example.gpsroutefinder.adapters;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.models.City;

import java.util.ArrayList;

public class CitySelectedListAdapter extends RecyclerView.Adapter<CitySelectedListAdapter.ViewHolder>   {

    ArrayList<City> selected_cities;
    Context context;
    ArrayList<Integer> position1;

    public CitySelectedListAdapter(Context context, ArrayList<City> selected_cities, ArrayList<Integer> position1) {
        this.selected_cities = selected_cities;
        this.context = context;
        this.position1 = position1;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_selected_cities, parent, false);

        Log.e("TAG","onCreateViewHolder:returnView" + view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("TAG","Array Position: "+ position1.get(position));
//        if (selected_cities != null && position < position1.size()) {
//            int selectedPosition = position1.get(position);

//            if (selectedPosition >= 0 && selectedPosition < selected_cities.size()) {
        City selectedCity = selected_cities.get(position1.get(position));

        Log.e("TAG", "City Name: " + selectedCity );

        holder.city_name.setText(selectedCity.getName());
        holder.city_time.setText(selectedCity.getTime());
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return position1 != null ? position1.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView city_name, city_time;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.e("ViewHolder:itemView", String.valueOf(itemView));
            city_name = itemView.findViewById(R.id.text1);
            city_time = itemView.findViewById(R.id.text2);

            layout = itemView.findViewById(R.id.layout_id);
            layout.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.add(getAdapterPosition(),1,0,"delete");
        }

    }
}
