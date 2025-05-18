package com.example.vowvenue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.VenueViewHolder> {

    private List<Venue> venueList;

    public VenueAdapter(List<Venue> venueList) {
        this.venueList = venueList;
    }

    @NonNull
    @Override
    public VenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venue, parent, false);
        return new VenueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder holder, int position) {
        Venue venue = venueList.get(position);
        holder.textViewName.setText(venue.getName());
        holder.textViewLocation.setText(venue.getLocation());
        holder.textViewCapacity.setText("Kapacitás: " + venue.getCapacity());
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    public static class VenueViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewLocation, textViewCapacity;

        public VenueViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewVenueName);
            textViewLocation = itemView.findViewById(R.id.textViewVenueLocation);
            textViewCapacity = itemView.findViewById(R.id.textViewVenueCapacity);
        }
    }
}
