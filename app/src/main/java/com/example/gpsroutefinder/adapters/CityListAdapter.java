package com.example.gpsroutefinder.adapters;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpsroutefinder.Interface.CityListCallbackAdapter;
import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.models.City;

import java.util.ArrayList;
import java.util.Locale;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> implements Filterable {

    ArrayList<City> cities;
    ArrayList<City> filtered_list;
    Filter filter;
    CityListCallbackAdapter callback;
    Context context;

    public CityListAdapter(Context context, CityListCallbackAdapter callback, ArrayList<City> cities) {
        this.cities = cities;
        this.filtered_list = cities;
        this.callback = callback;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.city_list_item_view, parent, false);

        Log.e("TAG","onCreateViewHolder:returnView" + view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(cities.get(position).getName());
        holder.time.setText(cities.get(position).getTime());


        holder.row_city.setOnClickListener(v -> {

            Log.e("TAG","callbacks: "+position);
            callback.onItemClicked(position);

        });
    }

    @Override
    public int getItemCount() {
        return filtered_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView time;
        LinearLayout row_city;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name =  itemView.findViewById(R.id.textView);
            time =  itemView.findViewById(R.id.timeView);
            row_city =  itemView.findViewById(R.id.row_city);
        }

    }

    @NonNull
    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CityFilter();
        }
        return filter;
    }
    private class CityFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            FilterResults results = new FilterResults();

            if (charSequence != null && charSequence.length()!= 0) {

                ArrayList<City> filteredList = new ArrayList<>();

                for (int i = 0; i < cities.size(); i++) {

                    if (cities.get(i).getName().toLowerCase(Locale.ROOT).contains(charSequence)) {
                        filteredList.add(cities.get(i));
                    }
                }
                results.values = filteredList;
                results.count = filteredList.size();
            }
            else{
                results.count = cities.size();
                results.values = cities;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtered_list = (ArrayList<City>) results.values;

            notifyDataSetChanged();
        }

    }
}
