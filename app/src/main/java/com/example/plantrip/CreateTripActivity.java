package com.example.plantrip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateTripActivity extends AppCompatActivity {

    private EditText tripNameEditText;
    private EditText destinationEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;
    private EditText tripTypeEditText;
    private EditText participantsEditText;
    private EditText transportationEditText;

    private DatabaseReference databaseTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        tripNameEditText = findViewById(R.id.tripNameEditText);
        destinationEditText = findViewById(R.id.destinationEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        tripTypeEditText = findViewById(R.id.tripTypeEditText);
        participantsEditText = findViewById(R.id.participantsEditText);
        transportationEditText = findViewById(R.id.transportationEditText);

        databaseTrips = FirebaseDatabase.getInstance().getReference("trips");

        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startDateEditText);
            }
        });

        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(endDateEditText);
            }
        });

        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTrip();
            }
        });
    }

    private void showDatePickerDialog(final EditText dateEditText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                dateEditText.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void createTrip() {
        String tripName = tripNameEditText.getText().toString().trim();
        String destination = destinationEditText.getText().toString().trim();
        String startDate = startDateEditText.getText().toString().trim();
        String endDate = endDateEditText.getText().toString().trim();
        String tripType = tripTypeEditText.getText().toString().trim();
        String participantsStr = participantsEditText.getText().toString().trim();
        String transportation = transportationEditText.getText().toString().trim();

        if (tripName.isEmpty() || destination.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || tripType.isEmpty() || participantsStr.isEmpty() || transportation.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
            return;
        }


        //veritabanına post etme işlemi
        String tripId = databaseTrips.push().getKey();
        List<String> participants = new ArrayList<>();
        for (String participant : participantsStr.split(",")) {
            participants.add(participant.trim());
        }

        Trip trip = new Trip(tripId, tripName, destination, startDate, endDate, tripType, participants, transportation);

        databaseTrips.child(tripId).setValue(trip);

        Intent intent = new Intent(CreateTripActivity.this, CreateLuggageActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
    }
}
