package com.example.gpsroutefinder.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpsroutefinder.R;
import com.example.gpsroutefinder.models.ExploreNearByRVModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ExploreNearByRVAdapter extends RecyclerView.Adapter<ExploreNearByRVAdapter.ViewHolder> {

    Context context;
    ArrayList<ExploreNearByRVModel> modelArrayList;

    LatLng latLng;

    public ExploreNearByRVAdapter(Context context, ArrayList<ExploreNearByRVModel> modelArrayList, LatLng latLng) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.latLng = latLng;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.explore_nearby_recycler_custom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.imageButton.setImageResource(modelArrayList.get(position).getImageButton());
        holder.textView.setText(modelArrayList.get(position).getTextView());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Construct the Uri using the latitude, longitude, and location name
                Uri gmmIntentUri = Uri.parse("geo:" + latLng+"?q=" + Uri.encode(modelArrayList.get(position).getTextView()));

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                // Add the FLAG_ACTIVITY_NEW_TASK flag
                mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Check if there is an app that can handle the intent
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageButton;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageButton = itemView.findViewById(R.id.imageButton);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
