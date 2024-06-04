package com.example.plantrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateLuggageActivity extends AppCompatActivity {

    private EditText itemNameEditText;
    private DatabaseReference databaseLuggage;
    private String tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_luggage);

        itemNameEditText = findViewById(R.id.itemNameEditText);

        tripId = getIntent().getStringExtra("tripId");
        databaseLuggage = FirebaseDatabase.getInstance().getReference("luggage").child(tripId);

        findViewById(R.id.addItemButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        findViewById(R.id.finishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishTrip();
            }
        });
    }

    private void addItem() {
        String itemName = itemNameEditText.getText().toString().trim();

        if (!itemName.isEmpty()) {
            String itemId = databaseLuggage.push().getKey();
            LuggageItem item = new LuggageItem(itemId, itemName);
            databaseLuggage.child(itemId).setValue(item);
            itemNameEditText.setText("");
            Toast.makeText(this, "Eşya eklendi", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lütfen bir eşya ismi girin", Toast.LENGTH_SHORT).show();
        }
    }

    private void finishTrip() {
        Intent intent = new Intent(CreateLuggageActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
