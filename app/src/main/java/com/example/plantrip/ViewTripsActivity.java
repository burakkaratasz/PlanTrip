package com.example.plantrip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewTripsActivity extends AppCompatActivity {

    private LinearLayout tripsContainer;
    private DatabaseReference databaseTrips;
    private DatabaseReference databaseLuggage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trips);

        tripsContainer = findViewById(R.id.tripsContainer);
        databaseTrips = FirebaseDatabase.getInstance().getReference("trips");
        databaseLuggage = FirebaseDatabase.getInstance().getReference("luggage");

        databaseTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tripsContainer.removeAllViews();
                for (DataSnapshot tripSnapshot : dataSnapshot.getChildren()) {
                    Trip trip = tripSnapshot.getValue(Trip.class);
                    if (trip != null) {
                        displayTrip(trip);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void displayTrip(Trip trip) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View tripView = inflater.inflate(R.layout.trip_card, tripsContainer, false);

        TextView tripNameTextView = tripView.findViewById(R.id.tripNameTextView);
        TextView tripDetailsTextView = tripView.findViewById(R.id.tripDetailsTextView);
        TextView luggageHeaderTextView = tripView.findViewById(R.id.luggageHeaderTextView);
        TextView luggageDetailsTextView = tripView.findViewById(R.id.luggageDetailsTextView);

        tripNameTextView.setText(trip.tripName);

        StringBuilder details = new StringBuilder();
        details.append("Gidilecek Yer: ").append(trip.destination).append("\n");
        details.append("Başlangıç Tarihi: ").append(trip.startDate).append("\n");
        details.append("Bitiş Tarihi: ").append(trip.endDate).append("\n");
        details.append("Seyahat Türü: ").append(trip.tripType).append("\n");
        if (trip.participants != null) {
            details.append("Katılacak Kişiler: ").append(String.join(", ", trip.participants)).append("\n");
        } else {
            details.append("Katılacak Kişiler: Yok\n");
        }
        details.append("Ulaşım Aracı: ").append(trip.transportation);
        tripDetailsTextView.setText(details.toString());

        databaseLuggage.child(trip.tripId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> luggageItems = new ArrayList<>();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    LuggageItem item = itemSnapshot.getValue(LuggageItem.class);
                    if (item != null) {
                        luggageItems.add(item.itemName);
                    }
                }
                if (!luggageItems.isEmpty()) {
                    luggageHeaderTextView.setVisibility(View.VISIBLE);
                    luggageDetailsTextView.setVisibility(View.VISIBLE);
                    luggageDetailsTextView.setText(String.join(", ", luggageItems));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        tripsContainer.addView(tripView);
    }
}
