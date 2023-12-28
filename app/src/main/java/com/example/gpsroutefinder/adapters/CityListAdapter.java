
package com.example.gpsroutefinder.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Locale;

import com.example.gpsroutefinder.Interface.CityListCallbackAdapter;
import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.models.City;

public class CityListAdapter extends ArrayAdapter<City> implements Filterable {

    ArrayList<City> cities;
    ArrayList<City> filtered_list;
    Filter filter;
    CityListCallbackAdapter callback;

    public CityListAdapter(Context context, int resource, CityListCallbackAdapter callback, ArrayList<City> cities) {
        super(context, resource, cities);
        this.cities = cities;
        this.filtered_list = cities;
        this.callback = callback;

    }
//    int getIndex(City city) {
//
//        int position = -1;
//
//        for (int i =0; i < cities.size() && position == -1; i++)
//        {
//            if(cities.get(i).isCity(city))
//            {
//                position = i;
//            }
//        }
//        return position;
//    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view,  ViewGroup parent) {

        ViewHolder viewHolder;

        if(view == null) {

            // Inflate the layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.city_list_item_view, parent,false);

            // Create a new ViewHolder and associate it with the view
            viewHolder = new ViewHolder();
            viewHolder.name =  view.findViewById(R.id.textView);
            viewHolder.time =  view.findViewById(R.id.timeView);
            viewHolder.row_city =  view.findViewById(R.id.row_city);

            // Use setTag() to store the ViewHolder with the view
            view.setTag(viewHolder);

            viewHolder.row_city.setOnClickListener(v -> {

                    Log.e("TAG","callbacks: "+position);
                    Toast.makeText(getContext(), "clicked item: "+position, Toast.LENGTH_SHORT).show();
                    callback.onItemClicked(position);

            });

        }else {
            // Reuse the existing ViewHolder from the recycled view
            viewHolder = (ViewHolder)view.getTag();
        }
        // Get the data item for this position
        City city = getItem(position);

        // Populate the data into the template view using the data object
        viewHolder.name.setText(city.getName());
        viewHolder.time.setText(city.getTime());

        return view;
    }

    private static class ViewHolder {
        TextView name;
        TextView time;
        LinearLayout row_city;
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

    public City getItem(int position){          // Retrieve the data item at the specified position
        return filtered_list.get(position);
    }

    @Override
    public int getCount() {
        Log.e("TAG","getItemCountFiltered_list: "+filtered_list.size());
        Log.e("TAG","getItemCountCities: "+cities.size());
        return filtered_list.size();
    }

}