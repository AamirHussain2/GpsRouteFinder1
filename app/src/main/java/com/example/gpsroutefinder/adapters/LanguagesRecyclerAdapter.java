package com.example.gpsroutefinder.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpsroutefinder.Interface.MyAdapterCallback;
import com.example.gpsroutefinder.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class LanguagesRecyclerAdapter extends RecyclerView.Adapter<LanguagesRecyclerAdapter.ViewHolder> implements Filterable {
    List<String> originalList;
    Context context;
    Boolean isChecked ;
    private final MyAdapterCallback callback;
    ArrayList<String> filteredArrayList;

    public LanguagesRecyclerAdapter(Context context, List<String> originalList, MyAdapterCallback callback, Boolean isChecked) {
        this.originalList = originalList;
        this.context = context;
        this.callback = callback;
        this.isChecked = isChecked;
        filteredArrayList = new ArrayList<>();
        filteredArrayList.addAll(originalList);

        Log.e("TAG: filteredArrayList","filteredArrayList: " +filteredArrayList.get(0));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.languages_recycler_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.textView.setText(originalList.get(position));

        // Set up item click listener
        holder.textView.setOnClickListener((view) -> {
            if (position != RecyclerView.NO_POSITION && callback != null) {
                callback.onItemClicked(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  originalList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewLanguage);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();
                Log.e("TAG: query","get Text: "+query);
                originalList.clear();
                if (query.length() == 0) {
                    originalList.addAll(filteredArrayList);
                } else {
                    for (String item : filteredArrayList) {
                        if (item.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))) {
                            originalList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults(); 
                results.values = filteredArrayList;
                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredArrayList = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
}




