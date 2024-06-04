package com.example.plantrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTripActivity extends AppCompatActivity {

    private EditText tripNameEditText;
    private EditText destinationEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;

    private DatabaseReference databaseTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        tripNameEditText = findViewById(R.id.tripNameEditText);
        destinationEditText = findViewById(R.id.destinationEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);

        databaseTrips = FirebaseDatabase.getInstance().getReference("trips");

        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTrip();
            }
        });
    }

    private void createTrip() {
        String tripName = tripNameEditText.getText().toString().trim();
        String destination = destinationEditText.getText().toString().trim();
        String startDate = startDateEditText.getText().toString().trim();
        String endDate = endDateEditText.getText().toString().trim();

        String tripId = databaseTrips.push().getKey();

        Trip trip = new Trip(tripId, tripName, destination, startDate, endDate);

        databaseTrips.child(tripId).setValue(trip);

        Intent intent = new Intent(CreateTripActivity.this, CreateLuggageActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
    }
}